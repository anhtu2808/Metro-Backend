package com.metro.route.mapper;

import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {StationMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LineMapper {
    @Mapping(target = "startStation", ignore = true)
    @Mapping(target = "finalStation", ignore = true)
    Line toEntity(LineCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startStation", ignore = true)
    @Mapping(target = "finalStation", ignore = true)
    void updateEntity(LineUpdateRequest request, @MappingTarget Line entity);

    @Mapping(target = "startStation", source = "startStation")
    @Mapping(target = "finalStation", source = "finalStation")
    LineResponse toResponse(Line entity);
}
