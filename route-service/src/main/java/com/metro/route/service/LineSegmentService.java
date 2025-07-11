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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
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

    public LineSegmentService(LineSegmentRepository repository, LineSegmentMapper entityMapper, StationRepository stationRepository, LineSegmentMapper lineSegmentMapper) {
        super(repository, entityMapper);
        this.stationRepository = stationRepository;
        this.lineSegmentMapper = lineSegmentMapper;
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

    public List<LineSegmentResponse> getByLineId(Long lineId) {
        var segments = ((LineSegmentRepository) repository).findByLine_IdOrderByOrder(lineId);
        return segments.stream()
                .map(lineSegmentMapper::toResponse)
                .toList();
    }

    public Long findLineIdByStartAndEndStations(Long startStationId, Long endStationId) {
        return ((LineSegmentRepository) repository)
                .findLineIdByStartAndEndStations(startStationId, endStationId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_STATION_COMBINATION));
    }
}
