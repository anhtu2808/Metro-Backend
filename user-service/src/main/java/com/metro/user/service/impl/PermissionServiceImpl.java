package com.metro.user.service.impl;

import com.metro.user.Exception.AppException;
import com.metro.user.dto.request.role.PermissionRequest;
import com.metro.user.dto.response.role.PermissionResponse;
import com.metro.user.entity.Permission;
import com.metro.user.enums.ErrorCode;
import com.metro.user.mapper.PermissionMapper;
import com.metro.user.repository.PermissionRepository;
import com.metro.user.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public PermissionResponse create(PermissionRequest request) {
        boolean exists = permissionRepository.existsByName(request.getName());
        if (exists) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse).toList();
    }


    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public PermissionResponse update(Long id, PermissionRequest request) {
        Permission existing = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        Permission updated = permissionRepository.save(existing);
        return permissionMapper.toPermissionResponse(updated);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @Override
    public void delete(Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new AppException(ErrorCode.PERMISSION_NOT_FOUND);
        }
        permissionRepository.deleteById(id);
    }

}
