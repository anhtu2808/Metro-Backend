package com.metro.order.saga;

import com.metro.order.TicketOrderMapper;
import com.metro.order.dto.response.DynamicPriceResponse;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.dto.response.VNPayResponse;
import com.metro.order.entity.SagaState;
import com.metro.order.entity.TicketOrder;
import com.metro.order.enums.SagaStatus;
import com.metro.order.exception.AppException;
import com.metro.order.exception.ErrorCode;
import com.metro.order.repository.SagaStateRepository;
import com.metro.order.repository.TicketOrderRepository;
import com.metro.order.repository.httpClient.DynamicPriceClient;
import com.metro.order.repository.httpClient.PaymentClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FareAdjustmentOrchestratorImpl implements FareAdjustmentOrchestrator {
    TicketOrderRepository ticketOrderRepository;
    DynamicPriceClient dynamicPriceClient;
    PaymentClient paymentClient;
    SagaStateRepository sagaStateRepository;
    TicketOrderMapper ticketOrderMapper;

    @Override
    @Transactional
    public TicketOrderResponse execute(Long ticketOrderId, Long newEndStationId, HttpServletRequest request) {
        TicketOrder ticketOrder = ticketOrderRepository.findById(ticketOrderId)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_ORDER_NOT_FOUND));
        DynamicPriceResponse dynamicPriceResponse = dynamicPriceClient
                .getPriceByStartAndEnd(ticketOrder.getLineId(), ticketOrder.getStartStationId(), newEndStationId)
                .getResult();
        BigDecimal oldPrice = ticketOrder.getPrice();
        BigDecimal newPrice = dynamicPriceResponse.getPrice();
        BigDecimal adjustmentAmount = newPrice.subtract(oldPrice);
        SagaState sagaState = SagaState.builder()
                .ticketOrderId(ticketOrderId)
                .adjustmentAmount(adjustmentAmount)
                .newEndStationId(newEndStationId)
                .status(SagaStatus.PENDING)
                .build();
        sagaState = sagaStateRepository.save(sagaState);
        if (adjustmentAmount.compareTo(BigDecimal.ZERO) > 0) {

            VNPayResponse vnPayResponse = paymentClient.createAdjustmentPayment(
                    sagaState.getSagaId(),
                    ticketOrderId,
                    adjustmentAmount,
                    "NCB"

            ).getResult();
            TicketOrderResponse response = ticketOrderMapper.toResponse(ticketOrder);
            log.info("Fare adjustment payment created successfully: {}", vnPayResponse);
            sagaState.setPaymentUrl(vnPayResponse.getPaymentUrl());
            sagaState.setStatus(SagaStatus.PENDING);
            sagaState = sagaStateRepository.save(sagaState);
            return response;

        } else {
            sagaState.setStatus(SagaStatus.COMPLETED);
            sagaState = sagaStateRepository.save(sagaState);
            ticketOrder.setEndStationId(newEndStationId);
            ticketOrder.setPrice(newPrice);
            ticketOrderRepository.save(ticketOrder);
            TicketOrderResponse response = ticketOrderMapper.toResponse(ticketOrder);
            return response;
        }
    }
    @Override
    @Transactional
    public void handlePaymentCallback(Long sagaId, boolean success, String reason) {
       SagaState sagaState = sagaStateRepository.findById(sagaId)
               .orElseThrow(() -> new AppException(ErrorCode.SAGA_STATE_NOT_FOUND));
       if (!SagaStatus.PENDING.equals(sagaState.getStatus())) {
           throw new AppException(ErrorCode.SAGA_STATE_INVALID_STATUS);
       }
       if (success){
           TicketOrder ticketOrder = ticketOrderRepository.findById(sagaState.getTicketOrderId())
                   .orElseThrow(() -> new AppException(ErrorCode.TICKET_ORDER_NOT_FOUND));
           BigDecimal newPrice = sagaState.getAdjustmentAmount();
           updateTicketOrder(ticketOrder, newPrice, sagaState.getNewEndStationId());
           sagaState.setStatus(SagaStatus.COMPLETED);

       }else {
           sagaState.setStatus(SagaStatus.FAILED);
              log.error("Fare adjustment payment failed: {}", reason);
       }
         sagaStateRepository.save(sagaState);
    }
    private void updateTicketOrder(TicketOrder ticketOrder, BigDecimal newPrice, Long newEndStationId) {
        log.info("Updating ticket order after successful payment adjustment.");
        ticketOrder.setPrice(newPrice);
        ticketOrder.setEndStationId(newEndStationId);
        ticketOrderRepository.save(ticketOrder);
    }
}