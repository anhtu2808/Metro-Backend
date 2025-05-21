package com.metro.user_service.service;

import com.metro.user_service.dto.request.user.UserRequest;
import com.metro.user_service.dto.response.user.UserResponse;
import com.metro.user_service.enums.RoleType;

public interface UserService {
    UserResponse createUser(UserRequest request, RoleType role);
    UserResponse updateUser(UserRequest request);
    UserResponse deleteUser(long id);

    UserResponse getUser(long id);
}