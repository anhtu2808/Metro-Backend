package com.metro.user.mapper;

import com.metro.user.dto.request.user.StudentVerificationRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.entity.StudentVerification;
import com.metro.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StudentVerificationMapper {

    StudentVerification toStudentVerification(StudentVerificationRequest request, User user);

    StudentVerificationResponse toStudentVerificationResponse(StudentVerification studentVerification);
}
