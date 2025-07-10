package com.metro.ticket.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.entity.DynamicPriceMaster;
import com.metro.ticket.exception.AppException;
import com.metro.ticket.exception.ErrorCode;
import com.metro.ticket.mapper.DynamicPriceMasterMapper;
import com.metro.ticket.repository.DynamicPriceMasterRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicPriceMasterService extends AbstractService<DynamicPriceMaster,
        DynamicPriceMasterRequest, DynamicPriceMasterUpdateRequest, DynamicPriceMasterResponse> {
    DynamicPriceMasterMapper dynamicPriceMasterMapper;
    DynamicPriceMasterRepository dynamicPriceMasterRepository;

    public DynamicPriceMasterService(DynamicPriceMasterRepository repository,
            DynamicPriceMasterMapper dynamicPriceMasterMapper
    ) {
        super(repository, dynamicPriceMasterMapper);
        this.dynamicPriceMasterMapper = dynamicPriceMasterMapper;
        this.dynamicPriceMasterRepository = repository;
    }

    @Override
    protected void beforeCreate(DynamicPriceMaster entity) {

    }

    @Override
    protected void beforeUpdate(DynamicPriceMaster oldEntity, DynamicPriceMaster newEntity) {
        dynamicPriceMasterMapper.updateEntity(oldEntity, newEntity);
    }

    @Override
    @PreAuthorize("hasAuthority('dynamicPriceMaster:create')")
    public DynamicPriceMasterResponse create(DynamicPriceMasterRequest request) {
        if (dynamicPriceMasterRepository.existsByLineId(request.getLineId())) {
            throw new AppException(ErrorCode.DYNAMIC_PRICE_MASTER_EXISTS);
        }
        return super.create(request);
    }

    @Override
    @PreAuthorize("hasAuthority('dynamicPriceMaster:update')")
    public DynamicPriceMasterResponse update(Long id, DynamicPriceMasterUpdateRequest request) {
        return super.update(id, request);
    }

    @Override
    @PreAuthorize("hasAuthority('dynamicPriceMaster:delete')")
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @PreAuthorize("hasAuthority('dynamicPriceMaster:read')")
    public DynamicPriceMasterResponse findById(Long id) {
        return super.findById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('dynamicPriceMaster:read')")
    public PageResponse<DynamicPriceMasterResponse> findAll(int page, int size, String arrange) {
        return super.findAll(page, size, arrange);
    }
}
