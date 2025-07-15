package com.metro.route.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.service.LineSegmentService;
import com.metro.route.service.StationService;
import com.metro.route.service.LineService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lines")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineController {
    LineService lineService;
    StationService stationService;
    LineSegmentService lineSegmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Tạo tuyến đường mới", description = "Tạo một tuyến đường mới dựa trên yêu cầu cung cấp")
    public ApiResponse<LineResponse> createLine(@Valid @RequestBody LineCreationRequest request) {
        LineResponse response = lineService.createLine(request);
        return ApiResponse.<LineResponse>builder()
                .result(response)
                .message("Line created successfully")
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin tuyến đường theo ID", description = "Trả về thông tin chi tiết của tuyến đường dựa trên ID")
    public ApiResponse<LineResponse> getLineById(@PathVariable("id") Long id) {
        LineResponse response = lineService.getLineById(id);
        return ApiResponse.<LineResponse>builder()
                .result(response)
                .message("Line fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả tuyến đường", description = "Trả về danh sách tất cả tuyến đường, hỗ trợ phân trang và sắp xếp")
    public ApiResponse<PageResponse<LineResponse>> getLines(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PageResponse<LineResponse> response = lineService.getLines(page, size, sort);
        return ApiResponse.<PageResponse<LineResponse>>builder()
                .result(response)
                .message("Lines fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật tuyến đường", description = "Cập nhật thông tin tuyến đường dựa trên ID và yêu cầu cập nhật")
    public ApiResponse<LineResponse> updateLine(
            @PathVariable Long id,
            @Valid @RequestBody LineUpdateRequest request) {
        LineResponse response = lineService.updateLine(id, request);
        return ApiResponse.<LineResponse>builder()
                .result(response)
                .message("Line updated successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Xóa tuyến đường", description = "Xóa tuyến đường dựa trên ID")
    public ApiResponse<Void> deleteLine(@PathVariable("id") Long id) {
        lineService.deleteLine(id);
        return ApiResponse.<Void>builder()
                .result(null)
                .message("Line deleted successfully")
                .code(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @GetMapping("/search/by-line-code")
    @Operation(summary = "Tìm tuyến đường theo mã tuyến", description = "Trả về danh sách tuyến đường theo mã tuyến, hỗ trợ phân trang và sắp xếp")
    public ApiResponse<PageResponse<LineResponse>> getLinesByLineCode(
            @RequestParam String lineCode,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort) {
        PageResponse<LineResponse> response = lineService.getLinesByLineCode(lineCode, page, size, sort);
        return ApiResponse.<PageResponse<LineResponse>>builder()
                .result(response)
                .message("Lines found by line code")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/{lineId}/segments")
    @Operation(summary = "Lấy danh sách segment theo tuyến", description = "Trả về danh sách segment của tuyến đường dựa trên lineId")
    public ApiResponse<List<LineSegmentResponse>> getSegmentsByLine(@PathVariable Long lineId) {
        return ApiResponse.<List<LineSegmentResponse>>builder()
                .result(lineSegmentService.getByLineId(lineId))
                .message("Segments fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/{lineId}/start-stations")
    @Operation(summary = "Lấy danh sách start stations theo tuyến", description = "Trả về danh sách start stations của tuyến đường dựa trên lineId")
    public ApiResponse<List<StartStationResponse>> getStartStationsByLineId(@PathVariable Long lineId) {
        return ApiResponse.<List<StartStationResponse>>builder()
                .result(stationService.getStartStationsByLineId(lineId))
                .message("Start stations fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @GetMapping("/{lineId}/start-stations/{startStationId}")
    @Operation(summary = "Lấy danh sách end stations và giá vé", description = "Trả về các trạm kết thúc (end stations) cùng với giá vé, dựa trên tuyến (lineId) và trạm bắt đầu (startStationId)")
    public ApiResponse<List<EndStationResponse>> getEndStations(
            @PathVariable Long lineId,
            @PathVariable Long startStationId) {
        return ApiResponse.<List<EndStationResponse>>builder()
                .result(stationService.getEndStations(lineId, startStationId))
                .message("End stations fetched successfully")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PostMapping("/{lineId}/stations")
    @Operation(summary = "Thêm stations vào tuyến", description = "Thêm danh sách stations vào tuyến đường dựa trên lineId")
    public ApiResponse<LineResponse> addStationsToLine(
            @PathVariable Long lineId,
            @RequestBody List<Long> stationIds) {
        LineResponse response = lineService.addStationsToLine(lineId, stationIds);
        return ApiResponse.<LineResponse>builder()
                .result(response)
                .message("Stations added to line successfully")
                .code(HttpStatus.OK.value())
                .build();
    }
}
