package com.metro.route.service;

import com.metro.route.dto.request.linesegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.lineSegment.LineSegmentResponse;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LineSegmentService {

    @PreAuthorize("hasAuthority('LINE_SEGMENT_UPDATE')")
    LineSegmentResponse updateLineSegment(Long id, LineSegmentUpdateRequest request);

    List<LineSegmentResponse> getByLineId(Long lineId);


}
