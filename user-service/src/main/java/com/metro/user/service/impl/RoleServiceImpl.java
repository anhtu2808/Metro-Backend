package com.metro.user.service.impl;

import com.metro.user.Exception.AppException;
import com.metro.user.dto.request.role.RoleRequest;
import com.metro.user.dto.response.role.RoleResponse;
import com.metro.user.enums.ErrorCode;
import com.metro.user.enums.RoleType;
import com.metro.user.mapper.RoleMapper;
import com.metro.user.repository.PermissionRepository;
import com.metro.user.repository.RoleRepository;
import com.metro.user.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public RoleResponse create(RoleRequest request) {
        RoleType roleType = RoleType.valueOf(request.getName());
        if (roleRepository.findByName(roleType).isPresent()) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        var permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissions()));
        var role = roleMapper.toRole(request, permissions);
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public RoleResponse update(Long id, RoleRequest request) {
        var existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        if (request.getName() != null &&
                (roleRepository.existsByName(RoleType.valueOf(request.getName())) &&
                !existingRole.getName().equals(RoleType.valueOf(request.getName())))) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        var permissions = new HashSet<>(permissionRepository.findAllById(request.getPermissions()));
        roleMapper.updateRole(existingRole, request, permissions);
        var updatedRole = roleRepository.save(existingRole);
        return roleMapper.toRoleResponse(updatedRole);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public void delete(Long role) {
    if (!roleRepository.existsById(role)) {
        throw new AppException(ErrorCode.ROLE_NOT_FOUND);
    }
        roleRepository.deleteById(role);
    }
}
