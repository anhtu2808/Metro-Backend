package com.metro.ticket.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.ticket.dto.response.DynamicPriceResponse;
import com.metro.ticket.service.DynamicPriceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamic-prices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicPriceController {
    DynamicPriceService dynamicPriceService;

    @PostMapping("/calculate")
    ApiResponse<List<DynamicPriceResponse>> calculateDynamicPriceById(@RequestParam("lineId") Long lineId) {
        return ApiResponse.<List<DynamicPriceResponse>>builder()
                .result(dynamicPriceService.calculateDynamicPriceById(lineId))
                .build();
    }

    @GetMapping("/{lineId}")
    ApiResponse<List<DynamicPriceResponse>> getDynamicPricesByLineId(@PathVariable("lineId") Long lineId) {
        return ApiResponse.<List<DynamicPriceResponse>>builder()
                .result(dynamicPriceService.getDynamicPricesByLineId(lineId))
                .build();
    }

    @GetMapping()
    ApiResponse<List<DynamicPriceResponse>> getDynamicPricesByLineIdAndStartStationId(
            @RequestParam("lineId") Long lineId,
            @RequestParam("startStationId") Long startStationId) {
        return ApiResponse.<List<DynamicPriceResponse>>builder()
                .result(dynamicPriceService.getDynamicPricesByLineIdAndStartStationId(lineId, startStationId))
                .build();
    }
}
