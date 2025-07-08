package com.metro.payment.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.payment.configuration.FeignClientConfig;
import com.metro.payment.dto.response.TicketOrderResponse;
import com.metro.payment.enums.TicketStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ticket-order-client",
        url = "${ORDER_SERVICE_HOST:http://localhost:8086}", configuration = FeignClientConfig.class
)
public interface TicketOrderClient {
    @GetMapping("/ticket-orders/{id}")
    ApiResponse<TicketOrderResponse> getTicketOrderById(@PathVariable("id") Long id);
    @PutMapping("/ticket-orders/{id}/status")
    void updateTicketOrderStatus(
            @PathVariable("id") Long ticketOrderId,
            @RequestParam("status") TicketStatus status,
            @RequestHeader("X-INTERNAL-SECRET") String internalSecret
    );
}
