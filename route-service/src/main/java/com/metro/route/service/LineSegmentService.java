package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.lineSegment.LineSegmentCreationRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.entity.Line;
import com.metro.route.entity.LineSegment;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.mapper.LineSegmentMapper;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineSegmentService extends AbstractService<
        LineSegment,
        LineSegmentCreationRequest,
        LineSegmentUpdateRequest,
        LineSegmentResponse> {
    StationRepository stationRepository;
    LineSegmentMapper lineSegmentMapper;
    private final LineSegmentRepository lineSegmentRepository;
    private final LineRepository lineRepository;

    public LineSegmentService(LineSegmentRepository repository, LineSegmentMapper entityMapper, StationRepository stationRepository, LineSegmentMapper lineSegmentMapper, LineSegmentRepository lineSegmentRepository, LineRepository lineRepository) {
        super(repository, entityMapper);
        this.stationRepository = stationRepository;
        this.lineSegmentMapper = lineSegmentMapper;
        this.lineSegmentRepository = lineSegmentRepository;
        this.lineRepository = lineRepository;
    }


    @Override
    protected void beforeCreate(LineSegment entity) {
        Station startStation = stationRepository.findById(entity.getStartStation().getId()).
                orElseThrow(() -> new AppException(ErrorCode.START_STATION_NOT_FOUND));
        Station endStation = stationRepository.findById(entity.getEndStation().getId()).
                orElseThrow(() -> new AppException(ErrorCode.END_STATION_NOT_FOUND));
    }
    @Override
    protected void beforeUpdate(LineSegment oldEntity, LineSegment newEntity) {
        if (newEntity.getStartStation() != null && newEntity.getStartStation().getId() != null) {
            Station startStation = stationRepository.findById(newEntity.getStartStation().getId()).
                    orElseThrow(() -> new AppException(ErrorCode.START_STATION_NOT_FOUND));
            oldEntity.setStartStation(startStation);
        }


        if (newEntity.getEndStation() != null && newEntity.getEndStation().getId() != null) {
            Station endStation = stationRepository.findById(newEntity.getEndStation().getId()).
                    orElseThrow(() -> new AppException(ErrorCode.END_STATION_NOT_FOUND));
            oldEntity.setEndStation(endStation);
        }
        lineSegmentMapper.updateEntity(oldEntity, newEntity);
    }
    @Override
    @PreAuthorize("hasAuthority('lineSegment:create')")
    public LineSegmentResponse create(LineSegmentCreationRequest request) {
        return super.create(request);
    }

    @Override
    @PreAuthorize("hasAuthority('lineSegment:read')")
    public LineSegmentResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
//    @PreAuthorize("hasAuthority('lineSegment:read')")
    public PageResponse<LineSegmentResponse> findAll(int page, int size, String arrange) {
        return super.findAll(page, size, arrange);
    }

    @Override
    @PreAuthorize("hasAuthority('lineSegment:update')")
    public LineSegmentResponse update(Long id, LineSegmentUpdateRequest request) {

        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('lineSegment:delete')")
    public void delete(Long id) {
        super.delete(id);
    }

    public PageResponse<StartStationResponse> findByStartStationId(Long lineId, int page, int size, String arrange) {
        if (lineId == null || lineId <= 0) {
            throw new AppException(ErrorCode.INVALID_LINE_ID);
        }
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        var pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, arrange));
        var pageData = lineSegmentRepository.findByLineId(lineId, pageable);
        var startStationList = pageData.getContent().stream()
                .map(lineSegmentMapper::toStartStationResponse)
                .toList();
        log.info("Fetched {} start stations for line id: {}", startStationList.size(), lineId);
        return PageResponse.<StartStationResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .pageSize(size)
                .data(startStationList)
                .build();
    }
    public PageResponse<EndStationResponse> findByEndStationId(Long lineId,Long startStationId, int page, int size, String arrange) {
        if (lineId == null || lineId <= 0) {
            throw new AppException(ErrorCode.INVALID_LINE_ID);
        }
        if (startStationId == null || startStationId <= 0) {
            throw new AppException(ErrorCode.INVALID_START_STATION_ID);
        }
        Line line = lineRepository.findById(lineId)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_NOT_FOUND));
        LineSegment startStation = lineSegmentRepository.findByLineIdAndStartStationId(lineId, startStationId);
        if (startStation == null ){
            throw new AppException(ErrorCode.START_STATION_NOT_FOUND);
        }
        var pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, arrange));
        var pageData = lineSegmentRepository.findByLineId(lineId, pageable);
        List<EndStationResponse> endStationList = pageData.getContent().stream()
                .filter(lineSegment -> !startStationId.equals(lineSegment.getEndStation().getId()))
                .map(lineSegment -> {
                    EndStationResponse response = lineSegmentMapper.toEndStationResponse(lineSegment);
                    BigDecimal fare = calculateFare(startStation.getOrder(), lineSegment.getOrder(), lineId);
                    response.setFare(fare);
                    return response;
                })
                .toList();
        log.info("Fetched {} end stations for line id: {}", endStationList.size(), lineId);
        return PageResponse.<EndStationResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .pageSize(size)
                .data(endStationList)
                .build();
    }
    private BigDecimal calculateFare(Integer startOrder, Integer endOrder, Long lineId) {
        float distance = 0;
        if (startOrder < endOrder) {
            List<LineSegment> segments = lineSegmentRepository.findByLineIdAndOrderBetween(lineId, startOrder, endOrder);
            for (LineSegment segment : segments) {
                distance += segment.getDistance();
            }
        } else if (startOrder > endOrder) {
            List<LineSegment> segments = lineSegmentRepository.findByLineIdAndOrderBetween(lineId, startOrder, endOrder);
            for (LineSegment segment : segments) {
                distance += segment.getDistance();
            }
        }
        else {
            return BigDecimal.valueOf(7);
        }
        // Calculate Fare
        float fare = 0;
        if (distance <= 7){
            fare = 7;
        }else {
            if (distance > 7){
                fare = distance + 1;
            }
        }
        return BigDecimal.valueOf(fare).setScale(0, RoundingMode.CEILING);
    }
}
