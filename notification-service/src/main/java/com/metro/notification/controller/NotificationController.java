package com.metro.notification.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.event.dto.NotificationEvent;
import com.metro.notification.service.EmailService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    EmailService emailService;

    // Test endpoint để gửi email trực tiếp mà không cần Kafka
    @PostMapping("/test-email")
    public ApiResponse<String> testEmail(@RequestParam String email, @RequestParam(required = false) String userName) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("userName", userName != null ? userName : "Test User");

            NotificationEvent event = NotificationEvent.builder()
                    .recipient(email)
                    .templateCode("welcome-email")
                    .subject("Test Email from Metro")
                    .param(params)
                    .build();

            emailService.sendEmailNotification(event);
            return ApiResponse.<String>builder()
                    .result("Email sent successfully to " + email)
                    .build();
        } catch (Exception e) {
            log.error("Failed to send test email", e);
            return ApiResponse.<String>builder()
                    .code(500)
                    .message("Failed to send email: " + e.getMessage())
                    .build();
        }
    }
}
