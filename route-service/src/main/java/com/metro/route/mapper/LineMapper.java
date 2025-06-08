package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.BusRoute;
import com.metro.route.entity.Line;
import com.metro.route.entity.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LineMapper extends EntityMappers<Line, LineCreationRequest, LineUpdateRequest, LineResponse> {
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Line oldEntity, Line newEntity);

    @Mapping(target = "startStation", source = "startStationId", qualifiedByName = "mapToStation")
    @Mapping(target = "finalStation", source = "finalStationId", qualifiedByName = "mapToStation")
    Line toEntity(LineCreationRequest request);

    @Mapping(target = "startStation", source = "startStationId", qualifiedByName = "mapToStation")
    @Mapping(target = "finalStation", source = "finalStationId", qualifiedByName = "mapToStation")
    Line updateToEntity(LineUpdateRequest request);

    @Named("mapToStation")
    default Station mapToStation(Long stationId) {
        if (stationId == null) {
            return null;
        }
        return Station.builder().id(stationId).build();
    }
}
