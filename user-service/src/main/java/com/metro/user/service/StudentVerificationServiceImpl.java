package com.metro.user.service;

import com.metro.user.dto.response.user.UserResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.metro.common_lib.dto.response.PageResponse;
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

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class StudentVerificationServiceImpl implements StudentVerificationService {
    UserRepository userRepository;
    StudentVerificationRepository studentVerificationRepository;
    StudentVerificationMapper studentVerificationMapper;
    UserService userService;

    @Override
    public StudentVerificationResponse createStudentVerification(StudentVerificationCreationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponse userResponse = userService.getMyInfo();
        User user = userRepository
                .findById(userResponse.getId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        StudentVerification studentVerification = studentVerificationMapper.toEntity(request);
        studentVerification.setUser(user);
        studentVerification = studentVerificationRepository.save(studentVerification);
        return studentVerificationMapper.toResponse(studentVerification);
    }

    @Override
    public StudentVerificationResponse getStudentVerificationById(Long id) {
        StudentVerification entity = studentVerificationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_VERIFICATION_NOT_FOUND));
        return studentVerificationMapper.toResponse(entity);
    }

    @Override
    public PageResponse<StudentVerificationResponse> getStudentVerifications(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<StudentVerification> pages = studentVerificationRepository.findAll(pageable);
        List<StudentVerificationResponse> data = pages.getContent().stream()
                .map(studentVerificationMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<StudentVerificationResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public StudentVerificationResponse updateStudentVerification(Long id, StudentVerificationUpdateRequest request) {
        StudentVerification entity = studentVerificationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_VERIFICATION_NOT_FOUND));
        studentVerificationMapper.updateEntity(request, entity);
        entity = studentVerificationRepository.save(entity);
        return studentVerificationMapper.toResponse(entity);
    }

    @Override
    public void deleteStudentVerification(Long id) {
        StudentVerification entity = studentVerificationRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_VERIFICATION_NOT_FOUND));
        studentVerificationRepository.delete(entity);
    }
}