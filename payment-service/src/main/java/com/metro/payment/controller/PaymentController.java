package com.metro.payment.controller;

import com.metro.payment.dto.response.ResponseObject;
import com.metro.payment.dto.response.VNPayResponse;
import com.metro.payment.service.PaymentService;
import com.metro.payment.service.impl.PaymentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;
    @GetMapping("/vn-pay")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseObject<VNPayResponse> pay(@RequestParam Long ticketOrderId,
                                             @RequestParam(required = false) String bankCode,HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVNPayPayment(ticketOrderId, bankCode, request));
    }
    @GetMapping("/vnpay-callback")
    public ResponseObject<VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        VNPayResponse response = paymentService.handleVNPayCallback(request);
        HttpStatus status = "00".equals(response.getCode()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseObject<>(status, response.getMessage(), response);
    }
}
