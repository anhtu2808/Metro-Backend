package com.metro.order.service;

import com.metro.common_lib.mapper.EntityMappers;
import com.metro.common_lib.service.AbstractService;
import com.metro.order.TicketOrderMapper;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.dto.response.UserResponse;
import com.metro.order.entity.TicketOrder;
import com.metro.order.exception.AppException;
import com.metro.order.exception.ErrorCode;
import com.metro.order.repository.TicketOrderRepository;
import com.metro.order.repository.httpClient.UserClient;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketOrderService extends AbstractService<
        TicketOrder,
        TicketOrderCreationRequest,
        TicketOrderUpdateRequest,
        TicketOrderResponse> {
    UserClient userClient;

    protected TicketOrderService(TicketOrderRepository repository, TicketOrderMapper entityMapper, UserClient userClient) {
        super(repository, entityMapper);
        this.userClient = userClient;
    }

    @Override
    protected void beforeCreate(TicketOrder entity) {

        if( entity.getUserId() == null) {
            log.error("User ID is required for creating a ticket order.");
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponse user = null;
        try {
            user = userClient.getUser(Long.parseLong(userId)).getResult();
        }catch (Exception e) {
            log.error("Error while fetching user by ID: {}", entity.getUserId(), e);
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if(entity.getTicketTypeId() == null) {
            log.error("Ticket type ID is required for creating a ticket order.");
            throw new AppException(ErrorCode.TICKET_TYPE_NOT_FOUND);
        }

        if(entity.getStartStationId() == null){
            log.error("Start station ID is required for creating a ticket order.");
            throw new AppException(ErrorCode.START_STATION_NOT_FOUND);
        }
        if (entity.getEndStationId() == null) {
            log.error("End station ID is required for creating a ticket order.");
            throw new AppException(ErrorCode.END_STATION_NOT_FOUND);
        }
    }

    @Override
    protected void beforeUpdate(TicketOrder oldEntity, TicketOrder newEntity) {

    }
}
