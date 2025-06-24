package com.metro.ticket.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.ticket.dto.request.ticketType.TicketTypeCreationRequest;
import com.metro.ticket.dto.request.ticketType.TicketTypeUpdateRequest;
import com.metro.ticket.dto.response.TicketTypeResponse;
import com.metro.ticket.entity.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface TicketTypeMapper extends EntityMappers<
        TicketType,
        TicketTypeCreationRequest,
        TicketTypeUpdateRequest,
        TicketTypeResponse
        > {
    TicketType toEntity(TicketTypeCreationRequest request);
    @Mapping(target = "isStatic",source = "static")
    TicketTypeResponse toResponse(TicketType entity);
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget TicketType oldEntity, TicketType newEntity);
}
