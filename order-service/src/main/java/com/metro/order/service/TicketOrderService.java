package com.metro.order.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.enums.TicketStatus;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;

public interface TicketOrderService {
    TicketOrderResponse createTicketOrder(TicketOrderCreationRequest request);

    TicketOrderResponse getTicketOrderById(Long id);

    void updateTicketOrderStatus(Long ticketOrderId, TicketStatus status);

    PageResponse<TicketOrderResponse> getOrdersByUserId(Long userId, int page, int size);

    @PreAuthorize("hasAuthority('TICKET_ORDER_READ_ALL')")
    PageResponse<TicketOrderResponse> getAllTicketOrders(TicketOrderFilterRequest req);

    String generateTicketToken(Long ticketOrderId);

    void updateTicketOrderStatusAndPurchase(Long ticketOrderId, TicketStatus status, LocalDateTime purchaseDate);
}
