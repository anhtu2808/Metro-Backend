package com.metro.order.repository.httpClient;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.order.dto.response.TicketTypeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ticket-service", url = "${app.services.ticket.url}")
public interface TicketTypeClient {
    @GetMapping("/internal/ticket-types")
    ApiResponse<TicketTypeResponse> getTicketTypesById(@PathVariable("ticketTypeId") Long ticketTypeId);
}
