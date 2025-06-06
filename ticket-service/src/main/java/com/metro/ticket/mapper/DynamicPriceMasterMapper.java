package com.metro.ticket.mapper;

import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.entity.DynamicPriceMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DynamicPriceMasterMapper {
    @Mapping(target = "dynamicPrices",ignore = true)
    @Mapping(target = "id",ignore = true)
    DynamicPriceMaster toPriceMaster(DynamicPriceMasterRequest request);

    DynamicPriceMasterResponse toPriceMasterResponse(DynamicPriceMaster dynamicPriceMaster);
}
