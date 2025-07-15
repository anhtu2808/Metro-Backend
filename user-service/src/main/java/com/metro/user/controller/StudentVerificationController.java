package com.metro.user.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.metro.user.dto.request.studentVerification.StudentVerificationCreationRequest;
import com.metro.user.dto.request.studentVerification.StudentVerificationUpdateRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.service.StudentVerificationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/student-verifications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StudentVerificationController{
    StudentVerificationService studentVerificationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StudentVerificationResponse> createStudentVerification(
            @Valid @RequestBody StudentVerificationCreationRequest request) {
        StudentVerificationResponse response = studentVerificationService.createStudentVerification(request);
        return ApiResponse.<StudentVerificationResponse>builder()
                .result(response)
                .message("Student verification created successfully")
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentVerificationResponse> getStudentVerificationById(@PathVariable("id") Long id) {
        StudentVerificationResponse response = studentVerificationService.getStudentVerificationById(id);
        return ApiResponse.<StudentVerificationResponse>builder()
                .result(response)
                .message("Student verification fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<StudentVerificationResponse>> getStudentVerifications(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PageResponse<StudentVerificationResponse> response = studentVerificationService.getStudentVerifications(page, size, sort);
        return ApiResponse.<PageResponse<StudentVerificationResponse>>builder()
                .result(response)
                .message("Student verifications fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentVerificationResponse> updateStudentVerification(
            @PathVariable Long id,
            @Valid @RequestBody StudentVerificationUpdateRequest request) {
        StudentVerificationResponse response = studentVerificationService.updateStudentVerification(id, request);
        return ApiResponse.<StudentVerificationResponse>builder()
                .result(response)
                .message("Student verification updated successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteStudentVerification(@PathVariable("id") Long id) {
        studentVerificationService.deleteStudentVerification(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .message("Student verification deleted successfully")
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }
}
