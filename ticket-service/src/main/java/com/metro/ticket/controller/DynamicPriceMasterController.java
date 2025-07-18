package com.metro.ticket.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.ticket.dto.request.DynamicPriceMasterRequest;
import com.metro.ticket.dto.request.DynamicPriceMasterUpdateRequest;
import com.metro.ticket.dto.response.DynamicPriceMasterResponse;
import com.metro.ticket.service.DynamicPriceMasterService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/dynamic-price-masters")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Validated
public class DynamicPriceMasterController {

    DynamicPriceMasterService dynamicPriceMasterService;

    @GetMapping
    public ApiResponse<PageResponse<DynamicPriceMasterResponse>> getDynamicPriceMasters(@RequestParam(defaultValue = "1") int page,
                                                                                        @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<PageResponse<DynamicPriceMasterResponse>>builder()
                .code(200)
                .message("Dynamic Price Masters retrieved successfully")
                .result(dynamicPriceMasterService.getDynamicPriceMasters(page, size))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DynamicPriceMasterResponse> getDynamicPriceMasterById(@PathVariable Long id) {
        return ApiResponse.<DynamicPriceMasterResponse>builder()
                .code(200)
                .message("Dynamic Price Master retrieved successfully")
                .result(dynamicPriceMasterService.getDynamicPriceMasterById(id))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DynamicPriceMasterResponse> createDynamicPriceMaster(@Valid @RequestBody DynamicPriceMasterRequest request) {
        return ApiResponse.<DynamicPriceMasterResponse>builder()
                .code(201)
                .message("Dynamic Price Master created successfully")
                .result(dynamicPriceMasterService.createDynamicPriceMaster(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DynamicPriceMasterResponse> updateDynamicPriceMaster(@PathVariable Long id,
                                                                            @Valid @RequestBody DynamicPriceMasterUpdateRequest request) {
        return ApiResponse.<DynamicPriceMasterResponse>builder()
                .code(200)
                .message("Dynamic Price Master updated successfully")
                .result(dynamicPriceMasterService.updateDynamicPriceMaster(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteDynamicPriceMaster(@PathVariable Long id) {
        dynamicPriceMasterService.deleteDynamicPriceMaster(id);
        return ApiResponse.<Void>builder()
                .code(204)
                .message("Dynamic Price Master deleted successfully")
                .build();
    }
}
