package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.route.dto.request.StationRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.entity.Station;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
@Mapper(
        config = DefaultConfigMapper.class
)
public interface StationMapper extends EntityMapper<Station, StationRequest, StationResponse> {
}
