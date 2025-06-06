package com.metro.ticket.mapper;

import com.metro.ticket.dto.response.DynamicPriceResponse;
import com.metro.ticket.entity.DynamicPrice;
import com.metro.ticket.entity.DynamicPriceMaster;
import org.mapstruct.Mapper;

import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DynamicPriceMapper {
   DynamicPriceResponse toDynamicPriceResponse(DynamicPrice dynamicPrice);
}
