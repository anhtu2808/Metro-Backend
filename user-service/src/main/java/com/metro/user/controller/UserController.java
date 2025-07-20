package com.metro.user.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.request.user.UserUpdateRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.enums.RoleType;
import com.metro.user.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody UserRequest request) {
        var result = userService.createUser(request, RoleType.CUSTOMER);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user:create')")
    public ApiResponse<UserResponse> createUser(@RequestBody UserRequest request) {
        var result = userService.createUser(request, request.getRoleType());
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        var result = userService.getMyInfo();
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long id) {
        var result = userService.updateUser(request, id);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<UserResponse> deleteUser(@PathVariable Long id) {
        var result = userService.deleteUser(id);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        var result = userService.getUser(id);
        return ApiResponse.<UserResponse>builder().result(result).build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        var result = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder().result(result).build();
    }

    @GetMapping("/role/{roleType}")
    public ApiResponse<List<UserResponse>> getUsersByRole(@PathVariable RoleType roleType) {
        var result = userService.getUsersByRole(roleType);
        return ApiResponse.<List<UserResponse>>builder().result(result).build();
    }

    @PutMapping("/{id}/unban")
    public ApiResponse<Void> unBanUser(@PathVariable Long id) {
        userService.unBanUser(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("User unbanned successfully")
                .build();
    }
}
