package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.mapper.LineMapper;
import com.metro.route.repository.LineRepository;
import com.metro.route.repository.StationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineService extends AbstractService<
        Line,
        LineCreationRequest,
        LineUpdateRequest,
        LineResponse
        > {
    StationRepository stationRepository;
    LineMapper lineMapper;


    public LineService(LineRepository repository, LineMapper entityMapper, StationRepository stationRepository, LineMapper lineMapper) {
        super(repository, entityMapper);
        this.stationRepository = stationRepository;
        this.lineMapper = lineMapper;
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
    @PreAuthorize("hasAuthority('line:read')")
    public LineResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('line:read')")
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
}
