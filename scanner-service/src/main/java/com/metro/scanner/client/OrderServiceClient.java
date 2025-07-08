package com.metro.scanner.client;


import com.metro.common_lib.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "order-service",
        url = "${ORDER_SERVICE_HOST:http://localhost:8086}"
)
public interface OrderServiceClient {


}