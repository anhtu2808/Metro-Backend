package com.metro.user.service;

import java.util.List;

import com.metro.user.dto.request.role.PermissionRequest;
import com.metro.user.dto.response.role.PermissionResponse;

public interface PermissionService {

    PermissionResponse create(PermissionRequest permissionRequest);

    List<PermissionResponse> getAll();

    PermissionResponse update(Long id, PermissionRequest permissionRequest);

    void delete(Long id);
}
