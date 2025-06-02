package com.metro.user.service;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.enums.RoleType;

public interface UserService {
    UserResponse createUser(UserRequest request, RoleType role);
    UserResponse updateUser(UserRequest request);
    UserResponse deleteUser(long id);

    UserResponse getUser(long id);
}