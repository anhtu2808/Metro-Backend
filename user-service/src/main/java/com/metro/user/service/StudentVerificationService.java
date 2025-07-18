package com.metro.user.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.user.dto.request.studentVerification.StudentVerificationCreationRequest;
import com.metro.user.dto.request.studentVerification.StudentVerificationUpdateRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface StudentVerificationService {
    StudentVerificationResponse createStudentVerification(StudentVerificationCreationRequest request);

    StudentVerificationResponse getStudentVerificationById(Long id);

    @PreAuthorize("hasAuthority('STUDENT_VERIFICATION_READ_ALL')")
    PageResponse<StudentVerificationResponse> getStudentVerifications(int page, int size, String sort);

    StudentVerificationResponse updateStudentVerification(Long id, StudentVerificationUpdateRequest request);

    @PreAuthorize("hasAuthority('STUDENT_VERIFICATION_DELETE')")
    void deleteStudentVerification(Long id);
}
