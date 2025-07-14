package com.metro.ticket.service;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import org.springframework.security.access.prepost.PreAuthorize;

public interface DynamicPriceMasterService {

    @PreAuthorize("hasAuthority('DYNAMIC_PRICE_MASTER_CREATE')")
    DynamicPriceMasterResponse createDynamicPriceMaster(DynamicPriceMasterRequest request);

    @PreAuthorize("hasAuthority('DYNAMIC_PRICE_MASTER_READ')")
    PageResponse<DynamicPriceMasterResponse> getDynamicPriceMasters(int page, int size);

    @PreAuthorize("hasAuthority('DYNAMIC_PRICE_MASTER_READ')")
    DynamicPriceMasterResponse getDynamicPriceMasterById(Long id);

    @PreAuthorize("hasAuthority('DYNAMIC_PRICE_MASTER_UPDATE')")
    DynamicPriceMasterResponse updateDynamicPriceMaster(Long id, DynamicPriceMasterUpdateRequest request);

    @PreAuthorize("hasAuthority('DYNAMIC_PRICE_MASTER_DELETE')")
    void deleteDynamicPriceMaster(Long id);
}
