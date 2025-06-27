package com.metro.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.user.dto.request.role.RoleRequest;
import com.metro.user.dto.response.role.RoleResponse;
import com.metro.user.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<RoleResponse> update(@PathVariable Long id, @RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.update(id, request))
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable Long role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
