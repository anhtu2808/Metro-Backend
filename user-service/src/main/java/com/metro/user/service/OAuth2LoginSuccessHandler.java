package com.metro.user.service;

import com.metro.user.entity.Role;
import com.metro.user.entity.User;
import com.metro.user.enums.RoleType;
import com.metro.user.mapper.UserMapper;
import com.metro.user.repository.RoleRepository;
import com.metro.user.repository.UserRepository;
import com.metro.user.service.impl.AuthenticationServiceImpl;
import com.metro.user.service.impl.RoleServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final AuthenticationServiceImpl authenticationService;
    private final RoleServiceImpl roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String fullName = oAuth2User.getAttribute("name");
        String avatarUrl = oAuth2User.getAttribute("picture");

        String[] nameParts = fullName.trim().split("\\s+");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length)) : "";

        User user;

        synchronized (this) {
            Optional<User> existingUser = userRepository.findByEmailWithRoleAndPermissions(email);
            if (existingUser.isPresent()) {
                user = existingUser.get();
            } else {
                Role role = roleService.getRoleWithPermissions(RoleType.CUSTOMER);
                User newUser = userMapper.googleOAuthToUser(email, firstName, lastName, avatarUrl, role);
                try {
                    user = userRepository.save(newUser);
                } catch (Exception e) {
                    user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Email duplicated and cannot recover user"));
                }
            }
        }

        String token = authenticationService.generateAuthTokenForUser(user).getToken();

        String redirectUrl = "https://metro.anhtudev.works";
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
