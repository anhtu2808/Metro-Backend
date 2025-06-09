package com.metro.user.service;

import com.metro.common_lib.mapper.EntityMappers;
import com.metro.common_lib.service.AbstractService;
import com.metro.user.Exception.AppException;
import com.metro.user.dto.request.studentVerification.StudentVerificationCreationRequest;
import com.metro.user.dto.request.studentVerification.StudentVerificationUpdateRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.entity.StudentVerification;
import com.metro.user.entity.User;
import com.metro.user.enums.ErrorCode;
import com.metro.user.mapper.StudentVerificationMapper;
import com.metro.user.repository.StudentVerificationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentVerificationService extends AbstractService<
        StudentVerification,
        StudentVerificationCreationRequest,
        StudentVerificationUpdateRequest,
        StudentVerificationResponse> {
    StudentVerificationMapper studentVerificationMapper;
    protected StudentVerificationService(StudentVerificationRepository repository, StudentVerificationMapper entityMapper, StudentVerificationMapper studentVerificationMapper) {
        super(repository, entityMapper);
        this.studentVerificationMapper = studentVerificationMapper;
    }
//    Long id;
//
//    String schoolName;
//
//    String imageUrl;
//
//    LocalDate graduateDate;
//    StudentVerificationStatus status;
//
//    User user;
    @Override
    protected void beforeCreate(StudentVerification entity) {
        if (entity.getUser().getId() == null) {
            throw new AppException(ErrorCode.STUDENT_USER_NOT_FOUND);
        }
    }

    @Override
    protected void beforeUpdate(StudentVerification oldEntity, StudentVerification newEntity) {
        if (newEntity.getUser().getId() == null){
            throw new AppException(ErrorCode.STUDENT_USER_NOT_FOUND);
        }
        studentVerificationMapper.updateToEntity(oldEntity,newEntity);

    }
}
