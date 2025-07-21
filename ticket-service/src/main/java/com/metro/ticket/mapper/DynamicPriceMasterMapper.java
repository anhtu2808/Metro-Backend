package com.metro.ticket.mapper;

import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.entity.DynamicPriceMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DynamicPriceMasterMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dynamicPrices", ignore = true)
    DynamicPriceMaster toEntity(DynamicPriceMasterRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dynamicPrices", ignore = true)
    void updateEntity(@MappingTarget DynamicPriceMaster existing, DynamicPriceMasterUpdateRequest request);

    DynamicPriceMasterResponse toResponse(DynamicPriceMaster entity);
}
