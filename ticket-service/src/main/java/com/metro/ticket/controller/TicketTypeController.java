package com.metro.ticket.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.ticket.dto.request.ticketType.TicketTypeCreationRequest;
import com.metro.ticket.dto.request.ticketType.TicketTypeUpdateRequest;
import com.metro.ticket.dto.response.TicketTypeResponse;
import com.metro.ticket.service.TicketTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/ticket-types")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(
        name = "Ticket Types",
        description = "API để quản lý các loại vé (Ticket Types) trong hệ thống metro"
)
public class TicketTypeController {
TicketTypeService ticketTypeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TicketTypeResponse> createTicketType(@Valid @RequestBody TicketTypeCreationRequest request) {
        TicketTypeResponse response = ticketTypeService.createTicketType(request);
        return ApiResponse.<TicketTypeResponse>builder()
                .result(response)
                .message("Ticket type created successfully")
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<TicketTypeResponse> getTicketTypeById(@PathVariable("id") Long id) {
        TicketTypeResponse response = ticketTypeService.getTicketTypeById(id);
        return ApiResponse.<TicketTypeResponse>builder()
                .result(response)
                .message("Ticket type fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<TicketTypeResponse>> getTicketTypes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PageResponse<TicketTypeResponse> response = ticketTypeService.getTicketTypes(page, size, sort);
        return ApiResponse.<PageResponse<TicketTypeResponse>>builder()
                .result(response)
                .message("Ticket types fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<TicketTypeResponse> updateTicketType(
            @PathVariable Long id,
            @Valid @RequestBody TicketTypeUpdateRequest request) {
        TicketTypeResponse response = ticketTypeService.updateTicketType(id, request);
        return ApiResponse.<TicketTypeResponse>builder()
                .result(response)
                .message("Ticket type updated successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteTicketType(@PathVariable("id") Long id) {
        ticketTypeService.deleteTicketType(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .message("Ticket type deleted successfully")
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }
}