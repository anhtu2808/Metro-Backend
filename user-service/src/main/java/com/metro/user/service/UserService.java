package com.metro.user.service;

import java.util.List;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.request.user.UserUpdateRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.enums.RoleType;
import com.metro.user.dto.request.user.UserFilterRequest;
import org.springframework.security.access.prepost.PreAuthorize;

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

    List<UserResponse> getAllUsers(UserFilterRequest filter);

    @PreAuthorize("hasAuthority('user:unban')")
    void unBanUser(Long id);
}
