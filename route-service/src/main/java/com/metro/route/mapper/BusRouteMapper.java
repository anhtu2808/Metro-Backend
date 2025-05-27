package com.metro.route_service.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.route_service.dto.request.BusRouteRequest;
import com.metro.route_service.dto.response.BusRouteResponse;
import com.metro.route_service.entity.BusRoute;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface BusRouteMapper extends EntityMapper<BusRoute, BusRouteRequest, BusRouteResponse> {
}
