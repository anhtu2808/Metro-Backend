package com.metro.ticket.mapper;

import com.metro.common_lib.mapper.EntityMappers;
import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.entity.DynamicPriceMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DynamicPriceMasterMapper extends EntityMappers<DynamicPriceMaster,
        DynamicPriceMasterRequest, DynamicPriceMasterUpdateRequest, DynamicPriceMasterResponse> {
    @Mapping(target = "dynamicPrices",ignore = true)
    @Mapping(target = "id",ignore = true)
    DynamicPriceMaster toEntity(DynamicPriceMasterRequest request);

//    DynamicPriceMasterResponse toPriceMasterResponse(DynamicPriceMaster dynamicPriceMaster);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dynamicPrices", ignore = true)
    DynamicPriceMaster updateToEntity(DynamicPriceMasterUpdateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dynamicPrices", ignore = true)
    void updateEntity(@MappingTarget DynamicPriceMaster oldEntity, DynamicPriceMaster newEntity);
}
