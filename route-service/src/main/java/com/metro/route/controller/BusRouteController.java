package com.metro.route.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.service.BusRouteService;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.response.BusRouteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus-routes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Bus Routes", description = "API để quản lý các tuyến xe buýt (Bus Routes) liên quan đến các ga trong hệ thống metro")
public class BusRouteController {

    BusRouteService busRouteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Tạo tuyến xe buýt mới", description = "Tạo một tuyến xe buýt mới dựa trên yêu cầu cung cấp")
    public ApiResponse<BusRouteResponse> createBusRoute(@Valid @RequestBody BusRouteCreationRequest request) {
        BusRouteResponse response = busRouteService.createBusRoute(request);
        return ApiResponse.<BusRouteResponse>builder()
                .result(response)
                .message("Bus route created successfully")
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin tuyến xe buýt theo ID", description = "Trả về thông tin chi tiết của tuyến xe buýt dựa trên ID")
    public ApiResponse<BusRouteResponse> getBusRouteById(@PathVariable("id") Long id) {
        BusRouteResponse response = busRouteService.getBusRouteById(id);
        return ApiResponse.<BusRouteResponse>builder()
                .result(response)
                .message("Bus route fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả tuyến xe buýt", description = "Trả về danh sách tất cả tuyến xe buýt, hỗ trợ phân trang và sắp xếp")
    public ApiResponse<PageResponse<BusRouteResponse>> getBusRoutes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PageResponse<BusRouteResponse> response = busRouteService.getBusRoutes(page, size, sort);
        return ApiResponse.<PageResponse<BusRouteResponse>>builder()
                .result(response)
                .message("Bus routes fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật tuyến xe buýt", description = "Cập nhật thông tin tuyến xe buýt dựa trên ID và yêu cầu cập nhật")
    public ApiResponse<BusRouteResponse> updateBusRoute(
            @PathVariable Long id,
            @Valid @RequestBody BusRouteUpdateRequest request) {
        BusRouteResponse response = busRouteService.updateBusRoute(id, request);
        return ApiResponse.<BusRouteResponse>builder()
                .result(response)
                .message("Bus route updated successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Xóa tuyến xe buýt", description = "Xóa tuyến xe buýt dựa trên ID")
    public ApiResponse<Void> deleteBusRoute(@PathVariable("id") Long id) {
        busRouteService.deleteBusRoute(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .message("Bus route deleted successfully")
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @GetMapping("/{stationId}/station")
    @Operation(summary = "Lấy danh sách các tuyến xe buýt theo Station ID", description = "Trả về danh sách các tuyến xe buýt (Bus Routes) liên quan đến ga được xác định bởi stationId, hỗ trợ phân trang và sắp xếp")
    public ApiResponse<PageResponse<BusRouteResponse>> getBusRoutesByStationId(
            @PathVariable("stationId") Long stationId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PageResponse<BusRouteResponse> response = busRouteService.getBusRoutesByStationId(stationId, page, size, sort);
        return ApiResponse.<PageResponse<BusRouteResponse>>builder()
                .result(response)
                .message(String.format("Bus routes found by station ID: %d", stationId))
                .code(HttpStatus.OK.value())
                .build();
    }
}
