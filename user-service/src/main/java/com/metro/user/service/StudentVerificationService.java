package com.metro.user.service;

import com.metro.common_lib.dto.response.PageResponse;
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
import com.metro.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.metro.user.enums.StudentVerificationStatus;

import java.time.LocalDate;
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentVerificationService extends AbstractService<
        StudentVerification,
        StudentVerificationCreationRequest,
        StudentVerificationUpdateRequest,
        StudentVerificationResponse> {
    UserRepository userRepository;
    StudentVerificationMapper studentVerificationMapper;
    protected StudentVerificationService(StudentVerificationRepository repository, StudentVerificationMapper entityMapper, UserRepository userRepository, StudentVerificationMapper studentVerificationMapper) {
        super(repository, entityMapper);
        this.userRepository = userRepository;
        this.studentVerificationMapper = studentVerificationMapper;
    }

    @Override
    protected void beforeCreate(StudentVerification entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        entity.setUser(user);
    }

    @Override
    protected void beforeUpdate(StudentVerification oldEntity, StudentVerification newEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        oldEntity.setUser(user);
        studentVerificationMapper.updateToEntity(oldEntity,newEntity);
    }
    @Override
//    @PreAuthorize("hasAuthority('student_verification:create')")
    public StudentVerificationResponse create(StudentVerificationCreationRequest request) {
        return super.create(request);
    }

    @Override
    @PreAuthorize("hasAuthority('student_verification:read')")
    public StudentVerificationResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('student_verification:read')")
    public PageResponse<StudentVerificationResponse> findAll(int page, int size, String sort) {
        return super.findAll(page, size, sort);
    }

    @Override
    @PreAuthorize("hasAuthority('student_verification:update')")
    public StudentVerificationResponse update(Long id, StudentVerificationUpdateRequest request) {
        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('student_verification:delete')")
    public void delete(Long id) {
        super.delete(id);
    }
}
