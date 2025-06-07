package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.common_lib.service.IService;
import com.metro.route.exception.ErrorCode;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.dto.response.BusRouteResponse;
import com.metro.route.entity.BusRoute;
import com.metro.route.entity.Station;
import com.metro.route.exception.AppException;
import com.metro.route.mapper.BusRouteMapper;
import com.metro.route.repository.BusRouteRepository;
import com.metro.route.repository.StationRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusRouteService extends AbstractService<
        BusRoute,
        BusRouteCreationRequest,
        BusRouteUpdateRequest,
        BusRouteResponse
        > {
    private final StationRepository stationRepository;
    private final BusRouteRepository busRouteRepository;
    BusRouteMapper busRouteMapper;

    public BusRouteService(BusRouteRepository repository, BusRouteMapper entityMapper, StationRepository stationRepository, BusRouteRepository busRouteRepository, BusRouteMapper busRouteMapper) {
        super(repository, entityMapper);
        this.stationRepository = stationRepository;
        this.busRouteRepository = busRouteRepository;
        this.busRouteMapper = busRouteMapper;
    }


    @Override
    protected void beforeCreate(BusRoute entity) {
        Long id = entity.getId();
        if (id != null && busRouteRepository.existsById(id)) {
            throw new AppException(ErrorCode.BUS_ROUTE_EXISTED);
        }
        Long stationId = entity.getStation().getId();
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
        entity.setStation(station);
    }

    @Override
    protected void beforeUpdate(BusRoute oldEntity, BusRoute newEntity) {
        if (newEntity.getStation() != null && newEntity.getStation().getId() != null) {
            Long newStationId = newEntity.getStation().getId();
            Station newStation = stationRepository.findById(newStationId)
                    .orElseThrow(() -> new AppException(ErrorCode.STATION_NOT_FOUND));
            oldEntity.setStation(newStation);
        }
        busRouteMapper.updateEntityFromRequest(newEntity, oldEntity);
    }
    @Override
    @PreAuthorize("hasAuthority('busroute:create')")
    public BusRouteResponse create(BusRouteCreationRequest request) {
        return super.create(request);
    }
    @Override
    @PreAuthorize("hasAuthority('busroute:read')")
    public BusRouteResponse findById(Long id) {
        return super.findById(id);
    }
    @Override
    @PreAuthorize("hasAuthority('busroute:read')")
    public PageResponse<BusRouteResponse> findAll(int page, int size, String arrange) {
        return super.findAll(page, size, arrange);
    }
    @Override
    @PreAuthorize("hasAuthority('busroute:update')")
    public BusRouteResponse update(Long id, BusRouteUpdateRequest request) {
        return super.update(id, request);
    }
    @Override
    @PreAuthorize("hasAuthority('busroute:delete')")
    public void delete(Long id) {
        super.delete(id);
    }
}