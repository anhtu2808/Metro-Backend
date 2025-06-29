package com.metro.order.service;

import com.metro.common_lib.mapper.EntityMappers;
import com.metro.common_lib.service.AbstractService;
import com.metro.order.TicketOrderMapper;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.dto.response.TicketOrderResponseEnricher;
import com.metro.order.dto.response.UserResponse;
import com.metro.order.entity.TicketOrder;
import com.metro.order.enums.TicketStatus;
import com.metro.order.exception.AppException;
import com.metro.order.exception.ErrorCode;
import com.metro.order.repository.TicketOrderRepository;
import com.metro.order.repository.httpClient.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketOrderService extends AbstractService<
        TicketOrder,
        TicketOrderCreationRequest,
        TicketOrderUpdateRequest,
        TicketOrderResponse> {
    UserClient userClient;
    TicketTypeClient ticketTypeClient;
    StationClient stationClient;
    DynamicPriceClient dynamicPriceClient;
    LineSegmentClient lineSegmentClient;
    TicketOrderRepository ticketOrderRepository;
    TicketOrderResponseEnricher responseEnricher;

    protected TicketOrderService(TicketOrderRepository ticketOrderRepository, TicketOrderMapper entityMapper,
                                 UserClient userClient,TicketTypeClient ticketTypeClient,
                                 StationClient stationClient,
                                 DynamicPriceClient dynamicPriceClient, LineSegmentClient lineSegmentClient,
                                 TicketOrderResponseEnricher responseEnricher) {
        super(ticketOrderRepository, entityMapper);
        this.userClient = userClient;
        this.ticketTypeClient = ticketTypeClient;
        this.stationClient = stationClient;
        this.dynamicPriceClient = dynamicPriceClient;
        this.lineSegmentClient = lineSegmentClient;
        this.ticketOrderRepository = ticketOrderRepository;
        this.responseEnricher = responseEnricher;
    }

    @Override
    protected void beforeCreate(TicketOrder entity) {
        if (entity.getUserId() == null) {
            try {
                var userInfo = userClient.getMyInfo().getResult();
                if (userInfo == null || userInfo.getId() == null) {
                    throw new AppException(ErrorCode.UNAUTHENTICATED);
                }
                entity.setUserId(userInfo.getId());
            } catch (Exception e) {
                log.error("Failed to get authenticated user info", e);
                throw new AppException(ErrorCode.USER_NOT_FOUND);
            }
        }
        var ticketType = ticketTypeClient.getTicketTypesById(entity.getTicketTypeId()).getResult();
        if (ticketType == null) {
            throw new AppException(ErrorCode.TICKET_TYPE_NOT_FOUND);
        }

        try {
            stationClient.getStationById(entity.getStartStationId()).getResult();
        } catch (Exception e) {
            throw new AppException(ErrorCode.START_STATION_NOT_FOUND);
        }

        try {
            stationClient.getStationById(entity.getEndStationId()).getResult();
        } catch (Exception e) {
            throw new AppException(ErrorCode.END_STATION_NOT_FOUND);
        }

        if (entity.getStartStationId().equals(entity.getEndStationId())) {
            throw new AppException(ErrorCode.INVALID_STATION_COMBINATION);
        }

        if (ticketType.isStatic()) {
            entity.setPrice(ticketType.getPrice());
        } else {
            Long lineId = lineSegmentClient
                    .getLineIdByStartAndEnd(entity.getStartStationId(), entity.getEndStationId())
                    .getResult();

            var dynamicPrice = dynamicPriceClient
                    .getPriceByStartAndEnd(lineId, entity.getStartStationId(), entity.getEndStationId())
                    .getResult();

            entity.setPrice(dynamicPrice.getPrice());
        }

        entity.setStatus(entity.getStatus() != null ? entity.getStatus() : TicketStatus.UNPAID);
        entity.setPurchaseDate(LocalDateTime.now());
        if (ticketType.getValidityDays() != null) {
            entity.setValidUntil(entity.getPurchaseDate().plusDays(ticketType.getValidityDays()));
        }
        LocalDateTime purchaseDate = entity.getPurchaseDate();

        long dailyCount =ticketOrderRepository.countByPurchaseDateBetween(
                purchaseDate.toLocalDate().atStartOfDay(),
                purchaseDate.toLocalDate().plusDays(1).atStartOfDay()
        );

        String datePart = purchaseDate.toLocalDate().toString().replace("-", "");
        String sequence = String.format("%03d", dailyCount + 1); // 001, 002...
        String ticketCode = String.format("TCKT-%s-%s", datePart, sequence);
        entity.setTicketCode(ticketCode);
    }


    @Override
    protected void beforeUpdate(TicketOrder oldEntity, TicketOrder newEntity) {

    }

    @Override
    public TicketOrderResponse create(TicketOrderCreationRequest request){
        TicketOrder entity = entityMapper.toEntity(request);
        beforeCreate(entity);
        TicketOrder saved = repository.save(entity);

        TicketOrderResponse response = entityMapper.toResponse(saved);
        responseEnricher.enrich(saved, response);
        return response;
    }

    @Override
    public TicketOrderResponse findById(Long id) {
        TicketOrder entity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TICKET_ORDER_NOT_FOUND));
        TicketOrderResponse response = entityMapper.toResponse(entity);
        responseEnricher.enrich(entity, response);
        return response;
    }
}
