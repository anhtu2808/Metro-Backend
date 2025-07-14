package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.entity.Station;
import org.mapstruct.*;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface StationMapper extends EntityMappers<Station, StationCreationRequest, StationUpdateRequest, StationResponse> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(Station request, @MappingTarget Station entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Station station, StationUpdateRequest request);

    @Mapping(target = "order", ignore = true)
    StartStationResponse toStartStationResponse(Station station);

    @Mapping(target = "order", ignore = true)
    EndStationResponse toEndStationResponse(Station station);
}
