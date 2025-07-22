package com.metro.order.saga.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.order.dto.response.FareAdjustmentReponse;
import com.metro.order.dto.response.TicketOrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface FareAjustmentMapper {
    @Mapping(target = "ticketType", source = "ticketType")
    @Mapping(target = "startStation", source = "startStation")
    @Mapping(target = "endStation", source = "endStation")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "sagaId", ignore = true)
    @Mapping(target = "vnPayResponse", ignore = true)
    FareAdjustmentReponse toFareAdjustmentResponse(TicketOrderResponse ticketOrderResponse);
}
