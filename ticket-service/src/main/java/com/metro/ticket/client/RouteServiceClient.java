package com.metro.ticket.client;


import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.ticket.dto.response.LineSegmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "route-service",
        url = "${ROUTE_SERVICE_HOST:http://localhost:8084}"
)
public interface RouteServiceClient {

    @GetMapping("/lines/{lineId}/segments")
    ApiResponse<List<LineSegmentResponse>> getSegmentsByLine(@PathVariable("lineId") Long lineId);
}