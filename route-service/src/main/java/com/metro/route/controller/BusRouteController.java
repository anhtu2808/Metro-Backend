package com.metro.route.controller;


import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.entity.BusRoute;
import com.metro.route.service.BusRouteService;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.response.BusRouteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus-routes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(
        name = "Bus Routes",
        description = "API để quản lý các tuyến xe buýt (Bus Routes) liên quan đến các ga trong hệ thống metro"
)
public class BusRouteController extends AbstractController<
        BusRoute,
        BusRouteCreationRequest,
        BusRouteUpdateRequest,
        BusRouteResponse> {
    private final BusRouteService busRouteService;

    public BusRouteController(BusRouteService service, BusRouteService busRouteService) {
        super(service);
        this.busRouteService = busRouteService;
    }
    @Operation(
            summary = "Lấy danh sách các tuyến xe buýt theo Station ID",
            description = "Trả về danh sách các tuyến xe buýt (Bus Routes) liên quan đến ga được xác định bởi stationId, hỗ trợ phân trang và sắp xếp"
    )
    @GetMapping("/{stationId}/station")
    public ApiResponse<PageResponse<BusRouteResponse>> findByStationId(
            @PathVariable("stationId") Long stationId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        PageResponse<BusRouteResponse> response = busRouteService.findByStationId(stationId, page, size, sort);
        return ApiResponse.<PageResponse<BusRouteResponse>>builder()
                .result(response)
                .message(String.format("Bus routes found by station ID: %d", stationId))                .code(200)
                .build();
    }
}
