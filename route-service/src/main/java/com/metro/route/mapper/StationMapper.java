package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.entity.Station;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
@Mapper(
        config = DefaultConfigMapper.class
)
public interface StationMapper extends EntityMappers<Station, StationCreationRequest, StationUpdateRequest, StationResponse> {
}
