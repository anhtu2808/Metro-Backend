package com.metro.route.service.impl;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.client.TicketServiceClient;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.DynamicPriceResponse;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.entity.Line;
import com.metro.route.entity.LineSegment;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.mapper.StationMapper;
import com.metro.route.repository.LineRepository;
import com.metro.route.repository.StationRepository;
import com.metro.route.service.StationService;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class StationServiceImpl implements StationService {
    StationRepository stationRepository;
    StationMapper stationMapper;
    LineRepository lineRepository;
    TicketServiceClient ticketServiceClient;

    @Override
    public StationResponse createStation(StationCreationRequest request) {
        Station station = stationMapper.toEntity(request);
        station = stationRepository.save(station);
        return stationMapper.toResponse(station);
    }

    @Override
    public StationResponse getStationById(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        return stationMapper.toResponse(station);
    }

    @Override
    public PageResponse<StationResponse> getStations(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<Station> pages = stationRepository.findAll(pageable);
        List<StationResponse> data = pages.getContent().stream()
                .map(stationMapper::toResponse)
                .toList();

        return PageResponse.<StationResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public StationResponse updateStation(Long id, StationUpdateRequest request) {
        Station existingStation = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        stationMapper.updateEntity(existingStation, request);
        Station updatedStation = stationRepository.save(existingStation);
        return stationMapper.toResponse(updatedStation);
    }

    @Override
    public void deleteStation(Long id) {
        Station station = stationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        stationRepository.delete(station);
    }

    @Override
    public List<StartStationResponse> getStartStationsByLineId(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        List<Station> stations = getStationByLine(line);
        Map<Long, Integer> orderMap = getStationOrders(line.getLineSegments(), line.getFinalStation().getId());
        return stations.stream()
                .map(s -> {
                    StartStationResponse resp = stationMapper.toStartStationResponse(s);
                    resp.setOrder(orderMap.get(s.getId()));
                    return resp;
                })
                .collect(Collectors.toList());
    }


    public List<EndStationResponse> getEndStations(Long lineId, Long startStationId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        List<Station> endStations = this.getStationByLine(line).stream().filter(
                station -> !station.getId().equals(startStationId)
        ).toList();
        Map<Long, Integer> stationOrder = getStationOrders(line.getLineSegments(), line.getFinalStation().getId());
        Map<Long, BigDecimal> dynamicPrices = ticketServiceClient.getDynamicPricesByLineIdAndStartStationId(lineId, startStationId).getResult()
                .stream()
                .collect(Collectors.toMap(DynamicPriceResponse::getEndStationId, DynamicPriceResponse::getPrice));
        return endStations.stream()
                .map(station -> {
                    EndStationResponse response = stationMapper.toEndStationResponse(station);
                    response.setOrder(stationOrder.get(station.getId()));
                    response.setFare(dynamicPrices.getOrDefault(station.getId(), BigDecimal.ZERO));
                    return response;
                })
                .toList();
    }

    private List<Station> getStationByLine(Line line) {
        List<LineSegment> lineSegments = line.getLineSegments();
        List<Station> stations = new ArrayList<>(lineSegments.stream()
                .map(LineSegment::getStartStation)
                .toList());
        stations.add(line.getFinalStation());
        return stations;
    }

    private Map<Long, Integer> getStationOrders(List<LineSegment> lineSegments, Long finalStationId) {
        Map<Long, Integer> stationOrder = new HashMap<>();
        for (LineSegment segment : lineSegments) {
            if (segment.getStartStation() != null && segment.getStartStation().getId() != null) {
                stationOrder.put(segment.getStartStation().getId(), segment.getOrder());
            }
        }
        stationOrder.put(finalStationId, lineSegments.size() + 1);
        return stationOrder;
    }
}
