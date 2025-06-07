package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.response.BusRouteResponse;
import com.metro.route.entity.BusRoute;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.repository.StationRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface BusRouteMapper extends EntityMappers<BusRoute, BusRouteCreationRequest, BusRouteUpdateRequest, BusRouteResponse> {
    BusRoute updateToEntity(BusRoute request);
    @Override
    default BusRoute toEntity(BusRouteCreationRequest request) {
        if (request == null) {
            return null;
        }
        BusRoute.BusRouteBuilder busRoute = BusRoute.builder();
        busRoute.startLocation(request.getStartLocation());
        busRoute.endLocation(request.getEndLocation());
        busRoute.headwayMinutes(request.getHeadwayMinutes());
        busRoute.distanceToStation(request.getDistanceToStation());
        busRoute.busStationName(request.getBusStationName());
        busRoute.busCode(request.getBusCode());
        busRoute.station(Station.builder().id(request.getStationId()).build());
        return busRoute.build();
    }
    @Override
    default BusRoute updateToEntity(BusRouteUpdateRequest request) {
        if (request == null) {
            return null;
        }
        BusRoute.BusRouteBuilder busRoute = BusRoute.builder();
        busRoute.startLocation(request.getStartLocation());
        busRoute.endLocation(request.getEndLocation());
        busRoute.headwayMinutes(request.getHeadwayMinutes());
        busRoute.distanceToStation(request.getDistanceToStation());
        busRoute.busStationName(request.getBusStationName());
        busRoute.busCode(request.getBusCode());
        busRoute.station(
                Station.builder()
                .id(request.getStationId())
                .build());
        return busRoute.build();
    }
}
