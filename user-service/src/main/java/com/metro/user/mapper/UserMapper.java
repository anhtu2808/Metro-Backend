package com.metro.user.mapper;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.entity.Role;
import com.metro.user.entity.User;
import com.metro.user.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper{

    @Mapping(target = "profile", source = "userProfile")
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "username", source = "request.username")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest request, UserProfile userProfile, Role role, String hashedPassword);
    UserResponse toUserResponse(User user);


}
