package com.metro.order.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.saga.FareAdjustmentOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket-orders")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InternalTicketController {
    final FareAdjustmentOrchestrator fareAdjustmentOrchestrator;
    @Value("${internal.secret}")
    String internalSecret;
    @PostMapping("/internal/adjust-fare/callback")
    public ApiResponse<Void> handleAdjustmentCallback(@RequestParam Long sagaId,
                                                      @RequestParam boolean success,
                                                      @RequestParam(required = false) String reason,
                                                      @RequestHeader(value = "X-INTERNAL-SECRET") String providedSecret) {
        log.info("💡 Provided secret: {}", providedSecret);
        log.info("🔐 Expected secret: {}", internalSecret);
        if (providedSecret == null || !providedSecret.equals(internalSecret)) {
            return ApiResponse.<Void>builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Unauthorized: Invalid internal secret")
                    .build();
        }
        log.info("Processing fare adjustment callback for sagaId: {}, success: {}, reason: {}", sagaId, success, reason);
        fareAdjustmentOrchestrator.handlePaymentCallback(sagaId, success, reason);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Fare adjustment callback processed successfully")
                .build();
    }
}
