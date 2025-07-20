package com.metro.order.saga;

import com.metro.order.dto.response.TicketOrderResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface FareAdjustmentOrchestrator {
    TicketOrderResponse execute(Long ticketOrderId, Long newEndStationId, HttpServletRequest request);
    void handlePaymentCallback(Long sagaId, boolean success, String reason);

}
