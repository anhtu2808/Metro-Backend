package com.metro.route.service.impl;

import com.metro.route.dto.response.StationResponse;
import com.metro.route.service.BusRouteService;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.response.BusRouteResponse;
import com.metro.route.entity.BusRoute;
import com.metro.route.entity.Station;
import com.metro.route.mapper.BusRouteMapper;
import com.metro.route.repository.BusRouteRepository;
import com.metro.route.repository.StationRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class BusRouteServiceImpl implements BusRouteService {
    BusRouteRepository busRouteRepository;
    StationRepository stationRepository;
    BusRouteMapper busRouteMapper;

    @Override
    public BusRouteResponse createBusRoute(BusRouteCreationRequest request) {
        BusRoute busRoute = busRouteMapper.toEntity(request);
        if (busRoute.getId() != null && busRouteRepository.existsById(busRoute.getId())) {
            throw new AppException(ErrorCode.BUS_ROUTE_EXISTED);
        }
        Long stationId = busRoute.getStation().getId();
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        busRoute.setStation(station);
        busRoute = busRouteRepository.save(busRoute);
        return busRouteMapper.toResponse(busRoute);
    }

    @Override
    public BusRouteResponse getBusRouteById(Long id) {
        BusRoute busRoute = busRouteRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUS_ROUTE_NOT_FOUND));
        return busRouteMapper.toResponse(busRoute);
    }

    @Override
    public PageResponse<BusRouteResponse> getBusRoutes(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<BusRoute> pages = busRouteRepository.findAll(pageable);
        List<BusRouteResponse> data = pages.getContent().stream()
                .map(busRouteMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<BusRouteResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public BusRouteResponse updateBusRoute(Long id, BusRouteUpdateRequest request) {
        BusRoute busRoute = busRouteRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUS_ROUTE_NOT_FOUND));
        busRouteMapper.updateEntity(request, busRoute);
        if (request.getStationId() != null) {
            Station newStation = stationRepository.findById(request.getStationId())
                    .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
            busRoute.setStation(newStation);
        }
        busRoute = busRouteRepository.save(busRoute);
        return busRouteMapper.toResponse(busRoute);
    }

    @Override
    public void deleteBusRoute(Long id) {
        BusRoute busRoute = busRouteRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BUS_ROUTE_NOT_FOUND));
        busRouteRepository.delete(busRoute);
    }

    @Override
    public PageResponse<BusRouteResponse> getBusRoutesByStationId(Long stationId, int page, int size, String sort) {
        if (sort == null || sort.isEmpty()) {
            sort = "id";
        }
        if (stationId == null) {
            throw new AppException(ErrorCode.STATION_NOT_FOUND);
        }
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<BusRoute> pages = busRouteRepository.findByStationId(stationId, pageable);
        List<BusRouteResponse> data = pages.getContent().stream()
                .map(busRouteMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<BusRouteResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }
}