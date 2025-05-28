package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.route.dto.request.BusRouteRequest;
import com.metro.route.dto.response.BusRouteResponse;
import com.metro.route.entity.BusRoute;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface BusRouteMapper extends EntityMapper<BusRoute, BusRouteRequest, BusRouteResponse> {
}
