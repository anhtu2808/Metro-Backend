package com.metro.ticket.service;
import org.springframework.security.access.prepost.PreAuthorize;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.ticket.dto.request.ticketType.TicketTypeCreationRequest;
import com.metro.ticket.dto.request.ticketType.TicketTypeUpdateRequest;
import com.metro.ticket.dto.response.TicketTypeResponse;
public interface TicketTypeService {
    @PreAuthorize("hasAuthority('TICKET_TYPE_CREATE')")
    TicketTypeResponse createTicketType(TicketTypeCreationRequest request);

    TicketTypeResponse getTicketTypeById(Long id);

    PageResponse<TicketTypeResponse> getTicketTypes(int page, int size, String sort);

    @PreAuthorize("hasAuthority('TICKET_TYPE_UPDATE')")
    TicketTypeResponse updateTicketType(Long id, TicketTypeUpdateRequest request);

    @PreAuthorize("hasAuthority('TICKET_TYPE_DELETE')")
    void deleteTicketType(Long id);
}
