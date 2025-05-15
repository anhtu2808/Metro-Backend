package com.metro.user_service.service;

import com.metro.common_lib.dto.request.UserRequest;
import com.metro.common_lib.dto.response.UserResponse;

public interface UserService {
    UserRequest createUser(UserResponse response);

    UserResponse updateUser(UserResponse response);

    UserResponse deleteUser(long id);

    UserResponse getUser(long id);
}
