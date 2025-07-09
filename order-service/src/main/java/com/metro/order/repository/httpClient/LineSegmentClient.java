package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "route-service",contextId = "lineSegmentClient", url = "${ROUTE_SERVICE_HOST:http://localhost:8084}", configuration = FeignClientConfig.class)
public interface LineSegmentClient {
    @GetMapping("/line-segments/find-line-id")
    ApiResponse<Long> getLineIdByStartAndEnd(
            @RequestParam("startStationId") Long startStationId,
            @RequestParam("endStationId") Long endStationId
    );
}
