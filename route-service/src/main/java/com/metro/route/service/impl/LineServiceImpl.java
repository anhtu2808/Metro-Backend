package com.metro.route.service.impl;

import com.metro.route.service.LineService;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;
import com.metro.route.entity.LineSegment;
import com.metro.route.entity.Station;
import com.metro.route.mapper.LineMapper;
import com.metro.route.repository.LineRepository;
import com.metro.route.repository.LineSegmentRepository;
import com.metro.route.repository.StationRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Builder
public class LineServiceImpl implements LineService {
    LineRepository lineRepository;
    StationRepository stationRepository;
    LineSegmentRepository lineSegmentRepository;
    LineMapper lineMapper;

    @Override
    public LineResponse createLine(LineCreationRequest request) {
        Line line = lineMapper.toEntity(request);
        Station startStation = stationRepository.findById(request.getStartStationId())
                .orElseThrow(() -> new AppException(ErrorCode.START_STATION_NOT_FOUND));
        Station finalStation = stationRepository.findById(request.getFinalStationId())
                .orElseThrow(() -> new AppException(ErrorCode.FINAL_STATION_NOT_FOUND));
        line.setStartStation(startStation);
        line.setFinalStation(finalStation);
        line = lineRepository.save(line);
        return lineMapper.toResponse(line);
    }

    @Override
    public LineResponse getLineById(Long id) {
        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        return lineMapper.toResponse(line);
    }

    @Override
    public PageResponse<LineResponse> getLines(int page, int size, String sort) {
        if (sort == null || sort.isEmpty()) {
            sort = "id";
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<Line> pages = lineRepository.findAll(pageable);
        List<LineResponse> data = pages.getContent().stream()
                .map(lineMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<LineResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public LineResponse updateLine(Long id, LineUpdateRequest request) {
        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        lineMapper.updateEntity(request, line);
        if (request.getStartStationId() != null) {
            Station startStation = stationRepository.findById(request.getStartStationId())
                    .orElseThrow(() -> new AppException(ErrorCode.START_STATION_NOT_FOUND));
            line.setStartStation(startStation);
        }
        if (request.getFinalStationId() != null) {
            Station finalStation = stationRepository.findById(request.getFinalStationId())
                    .orElseThrow(() -> new AppException(ErrorCode.FINAL_STATION_NOT_FOUND));
            line.setFinalStation(finalStation);
        }
        line = lineRepository.save(line);
        return lineMapper.toResponse(line);
    }

    @Override
    public void deleteLine(Long id) {
        Line line = lineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        lineRepository.delete(line);
    }

    @Override
    public PageResponse<LineResponse> getLinesByLineCode(String lineCode, int page, int size, String sort) {
        if (sort == null || sort.isEmpty()) {
            sort = "id";
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
        Page<Line> pages = lineRepository.findByLineCode(lineCode, pageable);
        List<LineResponse> data = pages.getContent().stream()
                .map(lineMapper::toResponse)
                .collect(Collectors.toList());
        return PageResponse.<LineResponse>builder()
                .data(data)
                .pageSize(pages.getSize())
                .totalPages(pages.getNumber())
                .totalElements(pages.getTotalElements())
                .currentPage(page)
                .build();
    }

    @Override
    public LineResponse addStationsToLine(Long lineId, List<Long> stationIds) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        List<Station> stations = stationRepository.findAllById(stationIds);
        if (stations.size() != stationIds.size()) {
            throw new AppException(ErrorCode.STATION_NOT_FOUND);
        }
        Map<Long, Integer> orderMap = new HashMap<>();
        for (int i = 0; i < stationIds.size(); i++) {
            orderMap.put(stationIds.get(i), i);
        }
        // Sắp xếp lại theo thứ tự đầu vào
        List<Station> sortedStations = stations.stream()
                .sorted(Comparator.comparingInt(s -> orderMap.get(s.getId())))
                .toList();
        if (sortedStations.isEmpty()) {
            throw new AppException(ErrorCode.STATION_NOT_FOUND);
        }
        lineSegmentRepository.deleteAllByLine_Id(lineId);
        List<LineSegment> segments = new ArrayList<>();
        for (int i = 0; i < sortedStations.size() - 1; i++) {
            Station startStation = sortedStations.get(i);
            Station endStation = sortedStations.get(i + 1);
            LineSegment segment = LineSegment.builder()
                    .line(line)
                    .startStation(startStation)
                    .endStation(endStation)
                    .order(i + 1)
                    .build();
            segments.add(segment);
        }
        lineSegmentRepository.saveAll(segments);
        return lineMapper.toResponse(line);
    }
}