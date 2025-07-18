package com.metro.notification.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.notification.dto.request.SendEmailRequest;
import com.metro.notification.dto.response.EmailResponse;
import com.metro.notification.service.EmailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<EmailResponse> sendEmail(@Valid @RequestBody SendEmailRequest request) {
        EmailResponse response = emailService.sendEmail(request);
        return ApiResponse.<EmailResponse>builder()
                .result(response)
                .message("Email sent successfully")
                .code(HttpStatus.CREATED.value())
                .build();
    }
}
