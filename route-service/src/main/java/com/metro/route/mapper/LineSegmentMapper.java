package com.metro.route.mapper;

import com.metro.route.dto.request.linesegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.entity.LineSegment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.BeanMapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {StationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LineSegmentMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "line", ignore = true)
    @Mapping(target = "startStation", ignore = true)
    @Mapping(target = "endStation", ignore = true)
    void updateEntity(@MappingTarget LineSegment existing, LineSegmentUpdateRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "startStation", source = "startStation")
    @Mapping(target = "endStation", source = "endStation")
    @Mapping(target = "endStationId", source = "endStation.id")
    @Mapping(target = "startStationId", source = "startStation.id")
    LineSegmentResponse toResponse(LineSegment entity);


}
