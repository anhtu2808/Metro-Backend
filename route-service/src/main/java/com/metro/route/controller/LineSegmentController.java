package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.lineSegment.LineSegmentCreationRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.entity.LineSegment;
import com.metro.route.service.LineSegmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/line-segments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(
        name = "Line Segments",
        description = "API để quản lý các đoạn tuyến đường (Line Segments), ga bắt đầu và ga kết thúc của hệ thống metro"
)
public class LineSegmentController extends AbstractController<
        LineSegment,
        LineSegmentCreationRequest,
        LineSegmentUpdateRequest,
        LineSegmentResponse
        > {

    private final LineSegmentService lineSegmentService;

    public LineSegmentController(AbstractService<LineSegment, LineSegmentCreationRequest, LineSegmentUpdateRequest, LineSegmentResponse> service, LineSegmentService lineSegmentService) {
        super(service);
        this.lineSegmentService = lineSegmentService;
    }
    @Operation(
            summary = "Lấy danh sách các ga bắt đầu theo Line ID",
            description = "Trả về danh sách các ga bắt đầu (start stations) của tuyến đường được xác định bởi lineId, hỗ trợ phân trang và sắp xếp"
    )
    @GetMapping("/{lineId}/start-stations")
    public ApiResponse<PageResponse<StartStationResponse>> findByStartStationId(
            @PathVariable @Min(value = 1, message = "Line ID phải lớn hơn 0") Long lineId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
            PageResponse<StartStationResponse> response = lineSegmentService.findByStartStationId(lineId, page, size, sort);
            return ApiResponse.<PageResponse<StartStationResponse>>builder()
                    .result(response)
                    .message("Start Station found")
                    .code(200)
                    .build();
    }
    @Operation(
            summary = "Lấy danh sách các ga đến theo Line ID và Start Station ID",
            description = "Trả về danh sách các ga đến (end stations) của tuyến đường được xác định bởi lineId, bắt đầu từ startStationId, hỗ trợ phân trang và sắp xếp"
    )
    @GetMapping("/{lineId}/end-stations")
    public ApiResponse<PageResponse<EndStationResponse>> findByEndStationId(
            @PathVariable @Min(value = 1, message = "Line ID phải lớn hơn 0") Long lineId,
            @RequestParam @NotNull(message = "Start Station ID không được để trống") Long startStationId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
            PageResponse<EndStationResponse> response = lineSegmentService.findByEndStationId(lineId,startStationId, page, size, sort);
            return ApiResponse.<PageResponse<EndStationResponse>>builder()
                    .result(response)
                    .message("End Station found")
                    .code(200)
                    .build();

    }
}
