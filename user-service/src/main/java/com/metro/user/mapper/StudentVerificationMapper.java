package com.metro.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.metro.user.dto.request.user.StudentVerificationRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.entity.StudentVerification;
import com.metro.user.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentVerificationMapper {

    @Mapping(target = "user",ignore = true)
    @Mapping(target = "id", ignore = true)
    StudentVerification toEntity(StudentVerificationCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user",ignore = true)
    void updateToEntity(@MappingTarget StudentVerification oldEntity, StudentVerification newEntity);

    @Named("mapToUser")
    default User mapToUser(Long userId){
        if(userId == null){
            return null;
        }
        return User.builder().id(userId).build();
    }
    StudentVerification toStudentVerification(StudentVerificationRequest request, User user);

    StudentVerificationResponse toStudentVerificationResponse(StudentVerification studentVerification);
}
