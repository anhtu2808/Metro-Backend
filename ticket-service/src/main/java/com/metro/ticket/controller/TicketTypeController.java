package com.metro.ticket.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.ticket.dto.request.ticketType.TicketTypeCreationRequest;
import com.metro.ticket.dto.request.ticketType.TicketTypeUpdateRequest;
import com.metro.ticket.dto.response.TicketTypeResponse;
import com.metro.ticket.entity.TicketType;
import com.metro.ticket.service.TicketTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(
        name = "Ticket Types",
        description = "API để quản lý các loại vé (Ticket Types) trong hệ thống metro"
)
public class TicketTypeController extends AbstractController<
        TicketType,
        TicketTypeCreationRequest,
        TicketTypeUpdateRequest,
        TicketTypeResponse
        > {

    public TicketTypeController(final TicketTypeService service) {
        super(service);
    }
}