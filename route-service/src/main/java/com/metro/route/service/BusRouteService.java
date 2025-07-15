package com.metro.route.service;

import org.springframework.security.access.prepost.PreAuthorize;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.response.BusRouteResponse;

public interface BusRouteService {

//    @PreAuthorize("hasAuthority('BUS_ROUTE_CREATE')")
    BusRouteResponse createBusRoute(BusRouteCreationRequest request);

    BusRouteResponse getBusRouteById(Long id);

    PageResponse<BusRouteResponse> getBusRoutes(int page, int size, String sort);

//    @PreAuthorize("hasAuthority('BUS_ROUTE_UPDATE')")
    BusRouteResponse updateBusRoute(Long id, BusRouteUpdateRequest request);

//    @PreAuthorize("hasAuthority('BUS_ROUTE_DELETE')")
    void deleteBusRoute(Long id);

    PageResponse<BusRouteResponse> getBusRoutesByStationId(Long stationId, int page, int size, String sort);
}