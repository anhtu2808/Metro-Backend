package com.metro.payment.service;

import com.metro.payment.dto.response.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    VNPayResponse createVNPayPayment(Long ticketOrderId, String bankCode, HttpServletRequest request);
    VNPayResponse handleVNPayCallback(HttpServletRequest request);
}
