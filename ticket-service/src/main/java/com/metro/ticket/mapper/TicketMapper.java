package com.metro.ticket.mapper;

import com.metro.common_lib.mapper.EntityMapper;
import com.metro.ticket.dto.request.TicketRequest;
import com.metro.ticket.dto.response.TicketResponse;
import com.metro.ticket.entity.Ticket;

public interface TicketMapper extends EntityMapper<Ticket, TicketRequest, TicketResponse> {
}
