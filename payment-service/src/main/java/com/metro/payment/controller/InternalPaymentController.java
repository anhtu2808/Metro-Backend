package com.metro.payment.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.payment.dto.response.ResponseObject;
import com.metro.payment.dto.response.VNPayResponse;
import com.metro.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalPaymentController {
    PaymentService paymentService;
    @GetMapping("/internal/vnpay-adjustment")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VNPayResponse> adjustmentPayment(@RequestParam Long sagaId,
                                                        @RequestParam Long ticketOrderId,
                                                        @RequestParam BigDecimal adjustmentAmount,
                                                        @RequestParam(required = false) String bankCode,
                                                        HttpServletRequest request) {
        return ApiResponse.<VNPayResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .result(paymentService.createAdjustmentPayment(sagaId, ticketOrderId, adjustmentAmount, bankCode, request))
                .build();
    }
}

