package com.metro.user_service.mapper;

import com.metro.user_service.dto.request.user.UserRequest;
import com.metro.user_service.dto.response.user.UserResponse;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.user_service.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserProfileMapper extends EntityMapper<UserProfile, UserRequest, UserResponse> {



}
