package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
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
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationService extends AbstractService<
        Station,
        StationCreationRequest,
        StationUpdateRequest,
        StationResponse> {
    StationMapper stationMapper;
    LineRepository lineRepository;
    TicketServiceClient ticketServiceClient;

    public StationService(StationRepository repository, StationMapper entityMapper, StationRepository stationRepository, StationMapper stationMapper, LineRepository lineRepository, TicketServiceClient ticketServiceClient1) {
        super(repository, entityMapper);

        this.stationMapper = stationMapper;
        this.lineRepository = lineRepository;
        this.ticketServiceClient = ticketServiceClient1;
    }

    @Override
    protected void beforeCreate(Station entity) {
    }

    @Override
    protected void beforeUpdate(Station oldEntity, Station newEntity) {
        stationMapper.updateEntityFromRequest(newEntity, oldEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('station:create')")
    public StationResponse create(StationCreationRequest request) {
        return super.create(request);
    }

    @Override
    public StationResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PageResponse<StationResponse> findAll(int page, int size, String arrange) {
        return super.findAll(page, size, arrange);
    }

    @Override
    @PreAuthorize("hasAuthority('station:update')")
    public StationResponse update(Long id, StationUpdateRequest request) {
        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('station:delete')")
    public void delete(Long id) {
        super.delete(id);
    }

    public List<StartStationResponse> getStartStationsByLineId(Long lineId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        List<Station> startStations = this.getStationByLine(line);
        Map<Long, Integer> stationOrder = getStationOrder(line.getLineSegments(), line.getFinalStation().getId());
        return startStations.stream()
                .map(station -> {
                    StartStationResponse response = stationMapper.toStartStationResponse(station);
                    response.setOrder(stationOrder.get(station.getId()));
                    return response;
                })
                .toList();
    }

    public List<EndStationResponse> getEndStations(Long lineId, Long startStationId) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        List<Station> endStations = this.getStationByLine(line).stream().filter(
                station -> !station.getId().equals(startStationId)
        ).toList();
        Map<Long, Integer> stationOrder = getStationOrder(line.getLineSegments(), line.getFinalStation().getId());
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

    private Map<Long, Integer> getStationOrder(List<LineSegment> lineSegments, Long finalStationId) {
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
