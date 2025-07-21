package com.metro.scanner.client;


import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.scanner.configuration.FeignClientConfig;
import com.metro.scanner.dto.response.TicketOrderResponse;
import com.metro.scanner.enums.TicketStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service",
        url = "${ORDER_SERVICE_HOST:http://localhost:8086}",
        configuration = FeignClientConfig.class
)
public interface OrderServiceClient {
    @GetMapping("/ticket-orders/{id}")
    ApiResponse<TicketOrderResponse> getTicketOrderById(@PathVariable Long id);

    @PutMapping("/ticket-orders/{id}/status")
    void updateTicketOrderStatus(@PathVariable Long id, @RequestParam(value = "status") TicketStatus status, @RequestHeader(value = "X-INTERNAL-SECRET") String internalSecretKey);
}