package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.entity.Line;
import com.metro.route.service.LineSegmentService;
import com.metro.route.service.LineService;
import com.metro.route.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lines")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineController extends AbstractController<
        Line,
        LineCreationRequest,
        LineUpdateRequest,
        LineResponse
        > {
    private final LineService lineService;
    private final StationService stationService;
    private final LineSegmentService lineSegmentService;

    public LineController(LineService service, LineService lineService, StationService stationService, LineSegmentService lineSegmentService) {
        super(service);
        this.lineService = lineService;
        this.stationService = stationService;
        this.lineSegmentService = lineSegmentService;
    }

    @GetMapping("/search/by-line-code")
    public ApiResponse<PageResponse<LineResponse>> findByLineCode(
          @RequestParam String lineCode,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            PageResponse<LineResponse> response = lineService.findByLineCode(lineCode, page, size, sort);
            return ApiResponse.<PageResponse<LineResponse>>builder()
                    .result(response)
                    .message("Lines found")
                    .code(200)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<PageResponse<LineResponse>>builder()
                    .result(null)
                    .code(500)
                    .message("Error finding lines: " + e.getMessage())
                    .build();
        }
    }
    @GetMapping("/{lineId}/segments")
    public ApiResponse<List<LineSegmentResponse>> getSegmentsByLine(@PathVariable Long lineId) {
        return ApiResponse.<List<LineSegmentResponse>>builder()
                .result(lineSegmentService.getByLineId(lineId))
                .build();
    }

    @GetMapping("/{lineId}/start-stations")
    public ApiResponse<List<StartStationResponse>> getStationByLineId(@PathVariable Long lineId) {
        return ApiResponse.<List<StartStationResponse>>builder()
                .result(stationService.getStartStationsByLineId(lineId))
                .message("Get start stations by line id successfully")
                .code(200)
                .build();
    }

    @Operation(
            summary = "Lấy danh sách end stations và giá vé",
            description = "Trả về các trạm kết thúc (end stations) cùng với giá vé, dựa trên tuyến (lineId) và trạm bắt đầu (startStationId)"
    )
    @GetMapping("/{lineId}/start-stations/{startStationId}")
    public ApiResponse<List<EndStationResponse>> getEndStations(
            @Parameter(description = "lineId") @PathVariable Long lineId,
            @Parameter(description = "truyền về id của station customer đã chon (start station)") @PathVariable Long startStationId
    ) {
        return ApiResponse.<List<EndStationResponse>>builder()
                .result(stationService.getEndStations(lineId, startStationId))
                .message("Get End Station by line id and start station id successfully")
                .code(200)
                .build();
    }
}
