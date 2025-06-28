package com.metro.payment.controller;

import com.metro.payment.dto.response.ResponseObject;
import com.metro.payment.dto.response.VNPayResponse;
import com.metro.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseObject<VNPayResponse> pay(@RequestParam Long ticketOrderId,
                                             @RequestParam(required = false) String bankCode,HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(ticketOrderId, bankCode, request));
    }
    @GetMapping("/vnpay-callback")
    public ResponseObject<VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        VNPayResponse response = paymentService.handleCallback(request);
        HttpStatus status = "00".equals(response.getCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseObject<>(status, response.getMessage(), response);
    }
}
