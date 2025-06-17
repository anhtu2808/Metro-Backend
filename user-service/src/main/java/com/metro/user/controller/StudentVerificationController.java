package com.metro.user.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.user.dto.request.studentVerification.StudentVerificationCreationRequest;
import com.metro.user.dto.request.studentVerification.StudentVerificationUpdateRequest;
import com.metro.user.dto.response.user.StudentVerificationResponse;
import com.metro.user.entity.StudentVerification;
import com.metro.user.service.StudentVerificationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student-verifications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentVerificationController extends AbstractController<
        StudentVerification,
        StudentVerificationCreationRequest,
        StudentVerificationUpdateRequest,
        StudentVerificationResponse
        > {
    public StudentVerificationController(StudentVerificationService service) {
        super(service);
    }

}
