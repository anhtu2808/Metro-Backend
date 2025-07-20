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
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        config = DefaultConfigMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TicketTypeMapper{
    TicketType toEntity(TicketTypeCreationRequest request);

    TicketTypeResponse toResponse(TicketType entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(TicketTypeUpdateRequest request, @MappingTarget TicketType entity);

}
