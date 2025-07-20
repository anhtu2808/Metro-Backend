package com.metro.payment.repository.httpClient;

import com.metro.payment.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "order-service-client",
        url = "${ORDER_SERVICE_URL:http://localhost:8086}",
        configuration = FeignClientConfig.class
)
public interface OrderClient {
    @PostMapping("/ticket-orders/internal/adjust-fare/callback")
    void handleAdjustmentCallback(
            @RequestParam Long sagaId,
            @RequestParam boolean success,
            @RequestParam(required = false) String reason,
            @RequestHeader("X-INTERNAL-SECRET") String internalSecret
    );

}
