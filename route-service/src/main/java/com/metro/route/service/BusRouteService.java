package com.metro.route.service;

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
        // Kiểm tra stationId có tồn tại không
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
        // Nếu có cập nhật stationId, kiểm tra và cập nhật station
        if (newEntity.getStation() != null && newEntity.getStation().getId() != null) {
            Long newStationId = newEntity.getStation().getId();
            Station newStation = stationRepository.findById(newStationId)
                    .orElseThrow(() -> new AppException(ErrorCode.BUS_ROUTE_NOT_FOUND));
            oldEntity.setStation(newStation);
        }
        oldEntity = busRouteMapper.updateToEntity(newEntity);
//
//        // Cập nhật các trường khác
//        if (newEntity.getStartLocation() != null) {
//            oldEntity.setStartLocation(newEntity.getStartLocation());
//        }
//        if (newEntity.getEndLocation() != null) {
//            oldEntity.setEndLocation(newEntity.getEndLocation());
//        }
//        if (newEntity.getHeadwayMinutes() > 0) {
//            oldEntity.setHeadwayMinutes(newEntity.getHeadwayMinutes());
//        }
//        if (newEntity.getDistanceToStation() != null) {
//            oldEntity.setDistanceToStation(newEntity.getDistanceToStation());
//        }
//        if (newEntity.getBusStationName() != null) {
//            oldEntity.setBusStationName(newEntity.getBusStationName());
//        }
//
//        // Cập nhật busCode nếu startLocation hoặc endLocation thay đổi
//        if (newEntity.getStartLocation() != null || newEntity.getEndLocation() != null) {
//            String newStartLocation = newEntity.getStartLocation() != null ? newEntity.getStartLocation() : oldEntity.getStartLocation();
//            String newEndLocation = newEntity.getEndLocation() != null ? newEntity.getEndLocation() : oldEntity.getEndLocation();
//        }
    }
}