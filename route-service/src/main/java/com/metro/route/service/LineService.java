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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
    LineMapper lineMapper;
    private final LineRepository lineRepository;


    public LineService(LineRepository repository, LineMapper entityMapper, StationRepository stationRepository, LineMapper lineMapper, LineRepository lineRepository) {
        super(repository, entityMapper);
        this.stationRepository = stationRepository;
        this.lineMapper = lineMapper;
        this.lineRepository = lineRepository;
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
}