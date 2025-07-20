package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.configuration.FeignClientConfig;
import com.metro.order.dto.response.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(
        name = "payment-service",
        url = "${PAYMENT_SERVICE_HOST:http://localhost:8081/payments}",
        configuration = FeignClientConfig.class
)
public interface PaymentClient {
    @GetMapping("/internal/vnpay-adjustment")
    ApiResponse<VNPayResponse> createAdjustmentPayment(
            @RequestParam Long sagaId,
            @RequestParam Long ticketOrderId,
            @RequestParam BigDecimal adjustmentAmount,
            @RequestParam String bankCode
//            @RequestParam String ipAddr
    );
}
