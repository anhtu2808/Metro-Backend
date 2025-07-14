package com.metro.user.service;

import java.util.List;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.request.user.UserUpdateRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.enums.RoleType;

public interface UserService {
    UserResponse createUser(UserRequest request, RoleType role);

    UserResponse updateUser(UserUpdateRequest request, Long id);

    UserResponse deleteUser(long id);

    UserResponse getUser(long id);

    /**
     * Retrieve information about the currently authenticated user.
     *
     * @return {@link UserResponse} representing the logged in user
     */
    UserResponse getMyInfo();

    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByRole(RoleType roleType);
}
