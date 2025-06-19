package com.metro.ticket.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.entity.DynamicPriceMaster;
import com.metro.ticket.service.DynamicPriceMasterService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dynamic-price-masters")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicPriceMasterController extends AbstractController<DynamicPriceMaster,
        DynamicPriceMasterRequest,
        DynamicPriceMasterUpdateRequest,
        DynamicPriceMasterResponse> {

    public DynamicPriceMasterController(DynamicPriceMasterService service) {
        super(service);
    }
}
