package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentCreationRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.LineSegmentResponse;
import com.metro.route.entity.Line;
import com.metro.route.entity.LineSegment;
import com.metro.route.entity.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LineSegmentMapper extends EntityMappers<LineSegment, LineSegmentCreationRequest, LineSegmentUpdateRequest, LineSegmentResponse> {
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget LineSegment oldEntity, LineSegment newEntity);

    @Mapping(target = "line", source = "lineId", qualifiedByName = "mapToLine")
    @Mapping(target = "startStation", source = "startStationId", qualifiedByName = "mapToStation")
    @Mapping(target = "endStation", source = "endStationId", qualifiedByName = "mapToStation")
    LineSegment toEntity(LineSegmentCreationRequest request);

    @Mapping(target = "line", source = "lineId", qualifiedByName = "mapToLine")
    @Mapping(target = "startStation", source = "startStationId", qualifiedByName = "mapToStation")
    @Mapping(target = "endStation", source = "endStationId", qualifiedByName = "mapToStation")
    LineSegment updateToEntity(LineSegmentUpdateRequest request);
    @Named("mapToStation")
    default Station mapToStation(Long stationId) {
        if (stationId == null) {
            return null;
        }
        return Station.builder().id(stationId).build();
    }
    @Named("mapToLine")
    default Line map(Long lineId) {
        return lineId == null
                ? null
                : Line.builder().id(lineId).build();
    }
    @Mapping(target = "lineId", source = "line.id")
    @Mapping(target = "startStationId", source = "startStation.id")
    @Mapping(target = "endStationId", source = "endStation.id")
    LineSegmentResponse toResponse(LineSegment entity);
}
