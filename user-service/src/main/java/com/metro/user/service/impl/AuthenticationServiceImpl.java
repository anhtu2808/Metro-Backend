package com.metro.user.service.impl;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.metro.user.event.EventBuilder;
import com.metro.user.entity.*;
import com.metro.user.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.metro.user.Exception.AppException;
import com.metro.user.dto.request.auth.AuthenticationRequest;
import com.metro.user.dto.request.auth.IntrospectRequest;
import com.metro.user.dto.request.auth.LogoutRequest;
import com.metro.user.dto.request.auth.RefreshRequest;
import com.metro.user.dto.response.auth.AuthenticationResponse;
import com.metro.user.dto.response.auth.IntrospectResponse;
import com.metro.user.enums.ErrorCode;
import com.metro.user.enums.RoleType;
import com.metro.user.mapper.UserMapper;
import com.metro.user.repository.InvalidatedTokenRepository;
import com.metro.user.repository.UserRepository;
import com.metro.user.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    final UserRepository userRepository;
    final InvalidatedTokenRepository invalidatedTokenRepository;
    final RoleServiceImpl roleService;
    final UserMapper userMapper;
    final ForgotPasswordRepository passwordOtpRepository;
    final EventBuilder eventBuilder;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException | JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INCORRECT_USERNAME_PASSWORD));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.INCORRECT_USERNAME_PASSWORD);
        if (user.getDeleted() == 1) throw new AppException(ErrorCode.USER_HAS_BEEN_BANNED);
        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken =
                    InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception) {
            log.info("Token already expired");
        }
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("metro.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        Role role = user.getRole();
        if (role != null) {
            // role grant
            stringJoiner.add("ROLE_" + role.getName());

            // permissions của role
            Set<Permission> perms = role.getPermissions();
            if (perms != null && !perms.isEmpty()) {
                perms.forEach(p -> stringJoiner.add(p.getName()));
            }
        }

        return stringJoiner.toString();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException {
        var signedJWT = verifyToken(request.getToken(), true);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse generateAuthTokenForUser(User user) {
        String token = generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }

    @Override
    public AuthenticationResponse authenticateWithGoogle(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance())
                    .setAudience(List.of("709789750534-gt40kns8437i96kl0olichbo0og1hv97.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String picture = (String) payload.get("picture");

            String[] nameParts = name != null ? name.trim().split("\\s+") : new String[0];
            String firstName = nameParts.length > 0 ? nameParts[0] : "";
            String lastName =
                    nameParts.length > 1 ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length)) : "";

            User user = userRepository.findByEmailWithRoleAndPermissions(email).orElseGet(() -> {
                Role role = roleService.getRoleWithPermissions(RoleType.CUSTOMER);
                User newUser = userMapper.googleOAuthToUser(email, firstName, lastName, picture, role);
                return userRepository.save(newUser);
            });

            return generateAuthTokenForUser(user);

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    public void forgotPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String otp = generateRandomOtp();
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(5);

        ForgotPassword passwordOtp = ForgotPassword.builder()
                .email(email)
                .otpCode(otp)
                .expiredAt(expiredAt)
                .used(false)
                .build();
        passwordOtpRepository.save(passwordOtp);
        eventBuilder.buildOtpEvent(email, user.getUsername(), otp);
    }

    @Override
    public void resetPassword(String email, String otpCode, String newPassword) {
        ForgotPassword otp = passwordOtpRepository
                .findTopByEmailAndOtpCodeAndUsedFalseOrderByExpiredAtDesc(email, otpCode)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_OTP));

        if (otp.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String hashedPassword = new BCryptPasswordEncoder(10).encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        otp.setUsed(true);
        passwordOtpRepository.save(otp);
    }

    private String generateRandomOtp() {
        int otp = new Random().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }
}
