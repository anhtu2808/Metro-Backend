package com.metro.user.service;

import com.metro.user.dto.request.role.PermissionRequest;
import com.metro.user.dto.response.role.PermissionResponse;
import com.metro.user.entity.Permission;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionRequest permissionRequest);
    List<PermissionResponse> getAll();
    PermissionResponse update(Long id, PermissionRequest permissionRequest);
    void delete(Long id);
}
