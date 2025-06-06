package com.metro.user.mapper;


import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.user.entity.Role;
import com.metro.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = {StudentVerificationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper{



    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "role", source = "role.name")
    UserResponse toUserResponse(User user);

    @Mapping(target = "isStudentVerified",ignore = true)
    @Mapping(target = "studentVerifications", ignore = true)
    @Mapping(target = "password", source = "hashedPassword")
    @Mapping(target = "role", source = "role")
    User toUser(UserRequest request, Role role, String hashedPassword);


}
