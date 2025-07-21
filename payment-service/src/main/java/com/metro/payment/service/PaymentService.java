package com.metro.payment.service;

import com.metro.payment.dto.response.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

public interface PaymentService {
    VNPayResponse createVNPayPayment(Long ticketOrderId, String bankCode, HttpServletRequest request);
    VNPayResponse handleVNPayCallback(HttpServletRequest request);
    VNPayResponse createAdjustmentPayment(
            Long sagaId,
            Long ticketOrderId,
            BigDecimal adjustmentAmount,
            String bankCode,
            HttpServletRequest request);
}
