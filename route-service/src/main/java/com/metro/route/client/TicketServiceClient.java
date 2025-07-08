package com.metro.route.client;


import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.route.dto.response.DynamicPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ticket-service",
        url = "${TICKET_SERVICE_HOST:http://localhost:8082}"
)
public interface TicketServiceClient {
    @GetMapping("/dynamic-prices")
    ApiResponse<List<DynamicPriceResponse>> getDynamicPricesByLineIdAndStartStationId(@RequestParam Long lineId, @RequestParam Long startStationId);
}