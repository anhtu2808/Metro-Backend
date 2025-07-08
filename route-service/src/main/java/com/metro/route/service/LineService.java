package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;
import com.metro.route.entity.LineSegment;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.mapper.LineMapper;
import com.metro.route.repository.LineRepository;
import com.metro.route.repository.LineSegmentRepository;
import com.metro.route.repository.StationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LineService extends AbstractService<
        Line,
        LineCreationRequest,
        LineUpdateRequest,
        LineResponse
        > {
    StationRepository stationRepository;
    LineSegmentRepository lineSegmentRepository;
    LineMapper lineMapper;
    private final LineRepository lineRepository;


    public LineService(LineRepository repository, LineMapper entityMapper, StationRepository stationRepository, LineMapper lineMapper, LineRepository lineRepository,LineSegmentRepository lineSegmentRepository) {
        super(repository, entityMapper);
        this.stationRepository = stationRepository;
        this.lineMapper = lineMapper;
        this.lineRepository = lineRepository;
        this.lineSegmentRepository = lineSegmentRepository;
    }

    @Override
    protected void beforeCreate(Line entity) {
        Station startStation = stationRepository.findById(entity.getStartStation().getId()).
                orElseThrow(() -> new AppException(ErrorCode.START_STATION_NOT_FOUND));
        Station finalStation = stationRepository.findById(entity.getFinalStation().getId()).
                orElseThrow(() -> new AppException(ErrorCode.FINAL_STATION_NOT_FOUND));
    }

    @Override
    protected void beforeUpdate(Line oldEntity, Line newEntity) {
        if (newEntity.getStartStation() != null && newEntity.getStartStation().getId() != null) {
            Station startStation = stationRepository.findById(newEntity.getStartStation().getId()).
                    orElseThrow(() -> new AppException(ErrorCode.START_STATION_NOT_FOUND));
            oldEntity.setStartStation(startStation);
        }


        if (newEntity.getFinalStation() != null && newEntity.getFinalStation().getId() != null) {
            Station finalStation = stationRepository.findById(newEntity.getFinalStation().getId()).
                    orElseThrow(() -> new AppException(ErrorCode.FINAL_STATION_NOT_FOUND));
            oldEntity.setFinalStation(finalStation);
        }
        lineMapper.updateEntity(oldEntity, newEntity);
    }
    @Override
    @PreAuthorize("hasAuthority('line:create')")
    public LineResponse create(LineCreationRequest request) {
        return super.create(request);
    }

    @Override
    public LineResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
    public PageResponse<LineResponse> findAll(int page, int size, String arrange) {
        return super.findAll(page, size, arrange);
    }

    @Override
    @PreAuthorize("hasAuthority('line:update')")
    public LineResponse update(Long id, LineUpdateRequest request) {
        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('line:delete')")
    public void delete(Long id) {
        super.delete(id);
    }

    public PageResponse<LineResponse> findByLineCode(String lineCode, int page, int size, String arrange) {
        if (arrange == null || arrange.isEmpty()) {
            arrange = "id";
        }
        var pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, arrange));
        var pageData = lineRepository.findByLineCode(lineCode, pageable);
        var lineList = pageData.getContent().stream()
                .map(lineMapper::toResponse)
                .toList();
        log.info("Fetched {} lines for line code: {}", lineList.size(), lineCode);
        return PageResponse.<LineResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(lineList)
                .build();
    }


    @PreAuthorize("hasAuthority('line:update')")
    public LineResponse addStationToLine(Long lineId, List<Long> stationIds) {
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        List<Station> stations = stationRepository.findAllByIdIn(stationIds);
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
        if (stations.isEmpty()) {
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