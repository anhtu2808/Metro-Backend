package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.dto.response.LineSegmentResponse;
import com.metro.route.entity.Line;
import com.metro.route.service.LineSegmentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    LineSegmentService lineSegmentService;

    public LineController(
            AbstractService<Line, LineCreationRequest, LineUpdateRequest, LineResponse> service,
            LineSegmentService lineSegmentService
    ) {
        super(service);
        this.lineSegmentService = lineSegmentService;
    }


    @GetMapping("/{lineId}/segments")
    public ApiResponse<List<LineSegmentResponse>> getSegmentsByLine(@PathVariable Long lineId) {
        return ApiResponse.<List<LineSegmentResponse>>builder()
                .result(lineSegmentService.findByLine(lineId))
                .build();
    }


}
