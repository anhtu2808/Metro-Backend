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

@Mapper(componentModel = "spring",
        uses = {StationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BusRouteMapper {
    @Mapping(target = "station.id", source = "stationId")
    BusRoute toEntity(BusRouteCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "station", ignore = true)
    void updateEntity(BusRouteUpdateRequest request, @MappingTarget BusRoute entity);

    @Mapping(target = "station", source = "station")
    @Mapping(target = "stationId", source = "station.id")
    BusRouteResponse toResponse(BusRoute entity);
}