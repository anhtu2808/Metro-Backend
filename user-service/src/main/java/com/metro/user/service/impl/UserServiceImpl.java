package com.metro.user.service.impl;

import com.metro.user.dto.request.user.UserUpdateRequest;
import com.metro.user.entity.Permission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metro.user.Exception.AppException;
import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.response.user.UserResponse;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request, RoleType roleType) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toUser(request, role, hashedPassword);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @Override
    public UserResponse updateUser(UserUpdateRequest request, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (request.getUsername() != null && !user.getUsername().equals(request.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (request.getEmail() != null && !user.getEmail().equals(request.getEmail())
                && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        userMapper.updateUserFromUpdateRequest(request, user);
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize(" hasAuthority('user:delete')")
    @Override
    public UserResponse deleteUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('user:read')")
    @Override
    public UserResponse getUser(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        Set<String> permissions = user.getRole().getPermissions().stream().map(Permission::getName).collect(Collectors.toSet());
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setPermissions(permissions);
        return userResponse;
    }

    @PreAuthorize("hasAnyAuthority('user:read')")
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }
}
