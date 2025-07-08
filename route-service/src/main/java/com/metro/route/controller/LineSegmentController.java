package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.lineSegment.LineSegmentCreationRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.entity.LineSegment;
import com.metro.route.service.LineSegmentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/line-segments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class LineSegmentController extends AbstractController<
        LineSegment,
        LineSegmentCreationRequest,
        LineSegmentUpdateRequest,
        LineSegmentResponse
        > {

    LineSegmentService lineSegmentService;
    public LineSegmentController(AbstractService<LineSegment, LineSegmentCreationRequest, LineSegmentUpdateRequest, LineSegmentResponse> service) {
        super(service);
        this.lineSegmentService = (LineSegmentService) service;
    }

    @GetMapping("/find-line-id")
    public ApiResponse<Long> findLineIdByStations(
            @RequestParam Long startStationId,
            @RequestParam Long endStationId
    ) {
        return ApiResponse.<Long>builder()
                .result(lineSegmentService.findLineIdByStartAndEndStations(startStationId, endStationId))
                .message("Found lineId from stations")
                .code(200)
                .build();
    }

}
