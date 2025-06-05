package com.metro.user.service.impl;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.Exception.AppException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Override
    public UserResponse updateUser(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse deleteUser(long id) {
        return null;
    }

    @Override
    public UserResponse getUser(long id) {
        return null;
    }
}
