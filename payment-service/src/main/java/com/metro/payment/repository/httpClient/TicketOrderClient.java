package com.metro.payment.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.payment.configuration.FeignClientConfig;
import com.metro.payment.dto.response.TicketOrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ticket-order-client",
        url = "${app.services.ticket_order.url}", configuration = FeignClientConfig.class
)
public interface TicketOrderClient {
    @GetMapping("/ticket-orders/{id}")
    ApiResponse<TicketOrderResponse> getTicketOrderById(@PathVariable("id") Long id);
}
