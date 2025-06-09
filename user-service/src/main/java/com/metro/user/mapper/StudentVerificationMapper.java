package com.metro.user.mapper;

import com.metro.common_lib.mapper.EntityMappers;
import com.metro.user.dto.request.studentVerification.StudentVerificationCreationRequest;
import com.metro.user.dto.request.studentVerification.StudentVerificationUpdateRequest;
import jdk.jfr.Name;
import org.mapstruct.*;

import com.metro.user.dto.request.user.StudentVerificationRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.entity.StudentVerification;
import com.metro.user.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentVerificationMapper extends EntityMappers<
        StudentVerification,
        StudentVerificationCreationRequest,
        StudentVerificationUpdateRequest,
        StudentVerificationResponse> {

    @Mapping(target = "user",source = "userId", qualifiedByName = "mapToUser")
    @Mapping(target = "id", ignore = true)
    StudentVerification toEntity(StudentVerificationCreationRequest request);

    StudentVerification toStudentVerification(StudentVerificationRequest request, User user);

//    StudentVerificationResponse toStudentVerificationResponse(StudentVerification studentVerification);
    @Named("mapToUser")
    default User mapToUser(Long userId){
        if(userId == null){
            return null;
        }
        return User.builder().id(userId).build();
    }
    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "user",source = "userId", qualifiedByName = "mapToUser")
    void updateToEntity(@MappingTarget StudentVerification oldEntity, StudentVerification newEntity);
}
