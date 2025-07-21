package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface StationService {

    @PreAuthorize("hasAuthority('STATION_CREATE')")
    StationResponse createStation(StationCreationRequest request);

    StationResponse getStationById(Long id);

    PageResponse<StationResponse> getStations(String search, int page, int size, String sort);

    @PreAuthorize("hasAuthority('STATION_UPDATE')")
    StationResponse updateStation(Long id, StationUpdateRequest request);

    @PreAuthorize("hasAuthority('STATION_DELETE')")
    void deleteStation(Long id);

    List<StartStationResponse> getStartStationsByLineId(Long lineId);

    List<EndStationResponse> getEndStations(Long lineId, Long startStationId);
}
