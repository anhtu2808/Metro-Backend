package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.configuration.FeignClientConfig;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.response.TicketTypeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ticket-service",contextId = "ticketType", url = "${TICKET_SERVICE_HOST:http://localhost:8082}", configuration = FeignClientConfig.class)
public interface TicketTypeClient {
    @GetMapping("/ticket-types/{id}")
    ApiResponse<TicketTypeResponse> getTicketTypesById(@PathVariable("id") Long id);

    @GetMapping("/ticket-types")
    ApiResponse<PageResponse<TicketTypeResponse>> getAllTicketTypes(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "1000") Integer size
    );
}
