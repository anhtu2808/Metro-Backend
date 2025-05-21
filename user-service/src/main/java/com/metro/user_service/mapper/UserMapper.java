package com.metro.user_service.mapper;

import com.metro.user_service.dto.request.user.UserRequest;
import com.metro.user_service.dto.response.user.UserResponse;
import com.metro.user_service.entity.Role;
import com.metro.user_service.entity.User;
import com.metro.user_service.entity.UserProfile;
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
