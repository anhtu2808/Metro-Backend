package com.metro.user.mapper;

import org.mapstruct.*;

import com.metro.common_lib.mapper.EntityMappers;
import com.metro.user.dto.request.studentVerification.StudentVerificationCreationRequest;
import com.metro.user.dto.request.studentVerification.StudentVerificationUpdateRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.entity.StudentVerification;
import com.metro.user.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentVerificationMapper
        extends EntityMappers<
                StudentVerification,
                StudentVerificationCreationRequest,
                StudentVerificationUpdateRequest,
                StudentVerificationResponse> {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    StudentVerification toEntity(StudentVerificationCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateToEntity(@MappingTarget StudentVerification oldEntity, StudentVerification newEntity);

    @Named("mapToUser")
    default User mapToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        return User.builder().id(userId).build();
    }
}
