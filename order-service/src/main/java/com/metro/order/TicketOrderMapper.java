package com.metro.order;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.order.dto.request.TicketOrderCreationRequest;
import com.metro.order.dto.request.TicketOrderUpdateRequest;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.entity.TicketOrder;
import com.metro.order.repository.httpClient.StationClient;
import com.metro.order.repository.httpClient.TicketTypeClient;
import com.metro.order.repository.httpClient.UserClient;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface TicketOrderMapper{
    TicketOrder toEntity(TicketOrderCreationRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "ticketType", ignore = true)
    @Mapping(target = "startStation", ignore = true)
    @Mapping(target = "endStation", ignore = true)
    TicketOrderResponse toResponse(TicketOrder entity);

    @Mapping(target = "userId", ignore = true)
    void updateEntity(TicketOrderUpdateRequest request, @MappingTarget TicketOrder entity);
}
