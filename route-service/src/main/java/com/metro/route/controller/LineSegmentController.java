package com.metro.route.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.route.dto.request.linesegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.service.LineSegmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/line-segments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class LineSegmentController {

    LineSegmentService lineSegmentService;

    @PutMapping("/{id}")
    public ApiResponse<LineSegmentResponse> updateLineSegment(
            @PathVariable Long id,
            @RequestBody LineSegmentUpdateRequest request) {
        return ApiResponse.<LineSegmentResponse>builder()
                .code(200)
                .message("Line segment updated successfully")
                .result(lineSegmentService.updateLineSegment(id, request))
                .build();
    }
}

