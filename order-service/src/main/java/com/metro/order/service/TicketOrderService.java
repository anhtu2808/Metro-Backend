package com.metro.order.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.enums.TicketStatus;

public interface TicketOrderService {
    TicketOrderResponse createTicketOrder(TicketOrderCreationRequest request);

    TicketOrderResponse getTicketOrderById(Long id);

    void updateTicketOrderStatus(Long ticketOrderId, TicketStatus status);

    PageResponse<TicketOrderResponse> getOrdersByUserId(Long userId, int page, int size);

    PageResponse<TicketOrderResponse> getAllTicketOrders(TicketOrderFilterRequest req);

    String generateTicketToken(Long ticketOrderId);
}
