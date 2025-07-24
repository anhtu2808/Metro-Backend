package com.metro.order.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.DashboardResponse;
import com.metro.order.dto.response.RevenueStatisticResponse;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.enums.TicketStatus;
import com.metro.order.enums.TimeGrouping;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketOrderService {
    TicketOrderResponse createTicketOrder(TicketOrderCreationRequest request);

    TicketOrderResponse getTicketOrderById(Long id);

    void updateTicketOrderStatus(Long ticketOrderId, TicketStatus status);

    PageResponse<TicketOrderResponse> getOrdersByUserId(Long userId, int page, int size);

    @PreAuthorize("hasAuthority('TICKET_ORDER_READ_ALL')")
    PageResponse<TicketOrderResponse> getAllTicketOrders(TicketOrderFilterRequest req);

    String generateTicketToken(Long ticketOrderId);

    @PreAuthorize("hasAuthority('TICKET_ORDER_UPDATE')")
    TicketOrderResponse updateTicketOrder(Long ticketOrderId, TicketOrderUpdateRequest request);

    void updateTicketOrderStatusAndPurchase(Long ticketOrderId, TicketStatus status, LocalDateTime purchaseDate);
    DashboardResponse getDashboardTicketOrder(LocalDateTime fromDate, LocalDateTime toDate);
    List<RevenueStatisticResponse> getRevenueStatistics(LocalDateTime fromDate, LocalDateTime toDate, TimeGrouping period);
    }
