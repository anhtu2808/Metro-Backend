package com.metro.route.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.entity.Station;
import com.metro.route.mapper.StationMapper;
import com.metro.route.repository.StationRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationService extends AbstractService<
        Station,
        StationCreationRequest,
        StationUpdateRequest,
        StationResponse> {
    StationMapper stationMapper;
    public StationService(StationRepository repository, StationMapper entityMapper, StationRepository stationRepository, StationMapper stationMapper) {
        super(repository, entityMapper);
        this.stationMapper = stationMapper;
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
    public StationResponse create(StationCreationRequest request){
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
}
