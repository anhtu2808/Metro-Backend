package com.metro.user.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.metro.event.dto.NotificationEvent;
import com.metro.user.service.NotificationEventProducer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metro.user.Exception.AppException;
import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.request.user.UserUpdateRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.entity.Permission;
import com.metro.user.entity.Role;
import com.metro.user.entity.User;
import com.metro.user.enums.ErrorCode;
import com.metro.user.enums.RoleType;
import com.metro.user.mapper.UserMapper;
import com.metro.user.repository.RoleRepository;
import com.metro.user.repository.UserRepository;
import com.metro.user.service.UserService;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    NotificationEventProducer notificationEventProducer;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request, RoleType roleType) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toUser(request, role, hashedPassword);
        user = userRepository.save(user);
        sendWelcomeNotification(user.getEmail(), user.getUsername());
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        List<String> roles = auth.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());

        if (roles.contains("ROLE_CUSTOMER")) {
            User currentUser = userRepository
                    .findByUsername(currentUsername)
                    .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));

            if (!currentUser.getId().equals(id)) {
                throw new AppException(ErrorCode.UNAUTHORIZED);
            }
        }
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (request.getUsername() != null && !Objects.equals(user.getUsername(), request.getUsername())) {
            var existingUser = userRepository.findByUsername(request.getUsername());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
                throw new AppException(ErrorCode.USER_EXISTED);
            }
        }

        if (request.getEmail() != null && !Objects.equals(user.getEmail(), request.getEmail())) {
            var existingUser = userRepository.findByEmail(request.getEmail());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }
        }

        userMapper.updateUserFromUpdateRequest(request, user);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize(" hasAuthority('user:delete')")
    @Override
    public UserResponse deleteUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('user:read') or hasAuthority('ticket_order:viewall')")
    @Override
    public UserResponse getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            var expiresAt = jwtAuth.getToken().getExpiresAt();
            if (expiresAt != null && expiresAt.isBefore(java.time.Instant.now())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }
        }
        String username = authentication.getName();
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Set<String> permissions = user.getRole().getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setPermissions(permissions);
        return userResponse;
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }
    private void sendWelcomeNotification(String email, String username) {
        try {
            NotificationEvent event = NotificationEvent.builder()
                    .channel("email")
                    .recipient(email)
                    .templateCode("welcome-email")
                    .param(Map.of(
                            "userName", username,
                            "customMessage", "Chúc mừng bạn đã đăng ký thành công!"
                    ))
                    .subject("Chào mừng bạn đến với Metro!")
                    .build();
            notificationEventProducer.sendWelcomeEmailEvent(event);
            log.info("Welcome notification sent for user: {}", username);
        } catch (Exception e) {
            log.error("Failed to send welcome notification for user: {}, error: {}", username, e.getMessage());
            // Không ném exception để không làm gián đoạn luồng chính
        }
    }
}
