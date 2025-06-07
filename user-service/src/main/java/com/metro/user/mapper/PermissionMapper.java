package com.metro.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.metro.user.dto.request.role.PermissionRequest;
import com.metro.user.dto.response.role.PermissionResponse;
import com.metro.user.entity.Permission;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PermissionMapper {

    @Mapping(target = "id", ignore = true)
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
