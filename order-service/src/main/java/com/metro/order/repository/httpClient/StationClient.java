package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.dto.response.StationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "route-service",contextId = "stationClient", url = "${ROUTE_SERVICE_HOST:http://localhost:8084}")
public interface StationClient {
    @GetMapping("/stations/{id}")
    ApiResponse<StationResponse> getStationById(@PathVariable("id") Long id);
}
