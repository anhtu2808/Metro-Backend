package com.metro.user_service.service.impl;

import com.metro.user_service.dto.request.user.UserRequest;
import com.metro.user_service.dto.response.user.UserResponse;
import com.metro.user_service.Exception.AppException;
import com.metro.user_service.entity.Role;
import com.metro.user_service.entity.User;
import com.metro.user_service.entity.UserProfile;
import com.metro.user_service.enums.ErrorCode;
import com.metro.user_service.enums.RoleType;
import com.metro.user_service.mapper.UserMapper;
import com.metro.user_service.mapper.UserProfileMapper;
import com.metro.user_service.repository.RoleRepository;
import com.metro.user_service.repository.UserProfileRepository;
import com.metro.user_service.repository.UserRepository;
import com.metro.user_service.service.UserService;
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
    UserProfileMapper userProfileMapper;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request, RoleType roleType) {
        if (userRepository.existsByUsername(request.getUsername())) throw new AppException(ErrorCode.USER_EXISTED);
        UserProfile userProfile = userProfileMapper.toEntity(request);
        Role role = roleRepository.findByName(roleType).orElseThrow();
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = userMapper.toUser(request, userProfile, role, hashedPassword);
        userProfile.setUser(user);
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
