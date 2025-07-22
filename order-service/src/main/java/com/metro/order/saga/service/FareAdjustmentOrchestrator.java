package com.metro.order.saga.service;

import com.metro.order.dto.response.FareAdjustmentReponse;
import jakarta.servlet.http.HttpServletRequest;

public interface FareAdjustmentOrchestrator {
    FareAdjustmentReponse execute(Long ticketOrderId, Long newEndStationId, HttpServletRequest request);
    void handlePaymentCallback(Long sagaId, boolean success, String reason);

}
