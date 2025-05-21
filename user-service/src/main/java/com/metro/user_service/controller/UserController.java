package com.metro.user_service.controller;

import com.metro.user_service.dto.request.user.UserRequest;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.user_service.dto.response.user.UserResponse;

import com.metro.user_service.enums.RoleType;
import com.metro.user_service.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserRequest request) {
        var result = userService.createUser(request, RoleType.CUSTOMER);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

}
