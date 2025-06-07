package com.metro.user.mapper;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.metro.user.dto.request.role.RoleRequest;
import com.metro.user.dto.response.role.RoleResponse;
import com.metro.user.entity.Permission;
import com.metro.user.entity.Role;

@Mapper(
        componentModel = "spring",
        uses = {PermissionMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "permissions", source = "permissions")
    Role toRole(RoleRequest request, Set<Permission> permissions);

    RoleResponse toRoleResponse(Role role);
}
