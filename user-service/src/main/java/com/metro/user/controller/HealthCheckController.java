package com.metro.user.controller;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.HeathCheckResponse;

@RestController
public class HealthCheckController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/health-check")
    public ApiResponse<HeathCheckResponse> healthCheck() {
        try {
            // Thực hiện truy vấn đơn giản để kiểm tra DB
            entityManager.createNativeQuery("SELECT 1").getSingleResult();

            HeathCheckResponse response = HeathCheckResponse.builder()
                    .status("OK")
                    .timestamp(LocalDateTime.now())
                    .build();
            return ApiResponse.<HeathCheckResponse>builder().result(response).build();
        } catch (Exception e) {
            HeathCheckResponse response = HeathCheckResponse.builder()
                    .status("DB_CONNECTION_FAILED")
                    .timestamp(LocalDateTime.now())
                    .build();

            return ApiResponse.<HeathCheckResponse>builder()
                    .result(response)
                    .message("Database connection failed: " + e.getMessage())
                    .code(500)
                    .build();
        }
    }
}
