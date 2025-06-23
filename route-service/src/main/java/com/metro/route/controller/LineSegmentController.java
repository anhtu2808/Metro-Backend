package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.lineSegment.LineSegmentCreationRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.LineSegmentResponse;
import com.metro.route.entity.LineSegment;
import com.metro.route.service.LineSegmentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/line-segments")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineSegmentController extends AbstractController<
        LineSegment,
        LineSegmentCreationRequest,
        LineSegmentUpdateRequest,
        LineSegmentResponse
        > {

    public LineSegmentController(AbstractService<LineSegment, LineSegmentCreationRequest, LineSegmentUpdateRequest, LineSegmentResponse> service) {
        super(service);
    }



}
