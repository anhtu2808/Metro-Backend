package com.metro.user.mapper;

import org.mapstruct.*;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.request.user.UserUpdateRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.entity.Role;
import com.metro.user.entity.User;

@Mapper(
        componentModel = "spring",
        uses = {StudentVerificationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "isStudentVerified", source = "studentVerified")
    UserResponse toUserResponse(User user);

    @Mapping(target = "isStudentVerified", ignore = true)
    @Mapping(target = "studentVerifications", ignore = true)
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest request, Role role, String hashedPassword);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUpdateRequest(UserUpdateRequest request, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", constant = "")
    @Mapping(target = "username", source = "email")
    @Mapping(target = "isStudentVerified", constant = "false")
    User googleOAuthToUser(String email, String firstName, String lastName, String avatarUrl, Role role);
}
