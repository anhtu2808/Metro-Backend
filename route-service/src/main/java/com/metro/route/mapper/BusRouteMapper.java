package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.response.BusRouteResponse;
import com.metro.route.entity.BusRoute;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.repository.StationRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface BusRouteMapper extends EntityMappers<BusRoute, BusRouteCreationRequest, BusRouteUpdateRequest, BusRouteResponse> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "station", ignore = true)
    void updateEntity(@MappingTarget BusRoute oldEntity,  BusRoute newEntity);

    @Mapping(target = "station", source = "stationId")
    BusRoute toEntity(BusRouteCreationRequest request);

    default Station map(Long stationId) {
        return stationId == null
                ? null
                : Station.builder().id(stationId).build();
    }
    @Mapping(target = "station", source = "stationId")
    BusRoute updateToEntity(BusRouteUpdateRequest request);
}