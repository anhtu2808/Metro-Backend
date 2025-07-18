package com.metro.ticket.service.impl;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.entity.DynamicPriceMaster;
import com.metro.ticket.exception.AppException;
import com.metro.ticket.exception.ErrorCode;
import com.metro.ticket.mapper.DynamicPriceMasterMapper;
import com.metro.ticket.repository.DynamicPriceMasterRepository;
import com.metro.ticket.service.DynamicPriceMasterService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicPriceMasterServiceImpl implements DynamicPriceMasterService {
    DynamicPriceMasterRepository repository;
    DynamicPriceMasterMapper mapper;

    @Override
    public DynamicPriceMasterResponse createDynamicPriceMaster(DynamicPriceMasterRequest request) {
        if (repository.existsByLineId(request.getLineId())) {
            throw new AppException(ErrorCode.DYNAMIC_PRICE_MASTER_EXISTS);
        }
        DynamicPriceMaster master = mapper.toEntity(request);
        master = repository.save(master);
        return mapper.toResponse(master);
    }

    @Override
    public PageResponse<DynamicPriceMasterResponse> getDynamicPriceMasters(int page, int size) {
        var pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        var pageData = repository.findAll(pageable);
        List<DynamicPriceMasterResponse> list = pageData.getContent().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return PageResponse.<DynamicPriceMasterResponse>builder()
                .currentPage(page)
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .data(list)
                .build();
    }

    @Override
    public DynamicPriceMasterResponse getDynamicPriceMasterById(Long id) {
        DynamicPriceMaster master = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DYNAMIC_PRICE_MASTER_NOT_FOUND));
        return mapper.toResponse(master);
    }

    @Override
    public DynamicPriceMasterResponse updateDynamicPriceMaster(Long id, DynamicPriceMasterUpdateRequest request) {
        DynamicPriceMaster existing = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DYNAMIC_PRICE_MASTER_NOT_FOUND));
        mapper.updateEntity(existing, request);
        existing = repository.save(existing);
        return mapper.toResponse(existing);
    }

    @Override
    public void deleteDynamicPriceMaster(Long id) {
        if (!repository.existsById(id)) {
            throw new AppException(ErrorCode.DYNAMIC_PRICE_MASTER_NOT_FOUND);
        }
        repository.deleteById(id);
    }
}
