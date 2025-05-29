package com.metro.user.mapper;

import com.metro.user.dto.request.user.UserRequest;
import com.metro.user.dto.response.user.UserResponse;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.user.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserProfileMapper extends EntityMapper<UserProfile, UserRequest, UserResponse> {



}
