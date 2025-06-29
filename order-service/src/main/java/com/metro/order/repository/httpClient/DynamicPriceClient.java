package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.dto.response.DynamicPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ticket-service",contextId = "dynamicPrice", url = "${TICKET_SERVICE_HOST:http://localhost:8082}")
public interface DynamicPriceClient {
    @GetMapping("/dynamic-prices/single")
    ApiResponse<DynamicPriceResponse> getPriceByStartAndEnd(
            @RequestParam("lineId") Long lineId,
            @RequestParam("startStationId") Long startStationId,
            @RequestParam("endStationId") Long endStationId
    );
}
