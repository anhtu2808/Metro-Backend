package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.response.BusRouteResponse;
import com.metro.route.entity.BusRoute;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface BusRouteMapper extends EntityMappers<BusRoute, BusRouteCreationRequest, BusRouteUpdateRequest, BusRouteResponse> {
}
