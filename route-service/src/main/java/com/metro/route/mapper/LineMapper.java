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

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LineMapper extends EntityMappers<Line, LineCreationRequest, LineUpdateRequest, LineResponse> {
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Line oldEntity, Line newEntity);
    @Override
    default Line toEntity(LineCreationRequest request) {
        if (request == null) {
            return null;
        }
        return Line.builder()
                .name(request.getName())
                .lineCode(request.getLineCode())
                .description(request.getDescription())
                .startStation(request.getStartStationId() == null ? null : Station.builder().id(request.getStartStationId()).build())
                .finalStation(request.getFinalStationId() == null ? null : Station.builder().id(request.getFinalStationId()).build())
                .build();
    }
    @Override
    default Line updateToEntity(LineUpdateRequest request) {
        if (request == null) {
            return null;
        }
        return Line.builder()
                .name(request.getName())
                .lineCode(request.getLineCode())
                .description(request.getDescription())
                .startStation(request.getStartStationId() == null ? null : Station.builder().id(request.getStartStationId()).build())
                .finalStation(request.getFinalStationId() == null ? null : Station.builder().id(request.getFinalStationId()).build())
                .build();
    }
}
