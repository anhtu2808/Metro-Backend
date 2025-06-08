package com.metro.user.service;

import com.metro.user.dto.request.role.RoleRequest;
import com.metro.user.dto.response.role.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest roleRequest);
    List<RoleResponse> getAll();
    RoleResponse update(Long id, RoleRequest roleRequest);
    void delete(Long id);
}
