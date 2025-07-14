package com.metro.route.service.impl;

import com.metro.route.dto.request.linesegment.LineSegmentUpdateRequest;

import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.entity.LineSegment;
import com.metro.route.exception.AppException;
import com.metro.route.exception.ErrorCode;
import com.metro.route.mapper.LineSegmentMapper;
import com.metro.route.repository.LineSegmentRepository;
import com.metro.route.service.LineSegmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineSegmentServiceImpl implements LineSegmentService {
    LineSegmentMapper mapper;
    LineSegmentRepository repository;

    public LineSegmentResponse updateLineSegment(Long id, LineSegmentUpdateRequest request) {
        LineSegment existingSegment = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LINE_SEGMENT_NOT_FOUND));
        mapper.updateEntity(existingSegment, request);
        LineSegment updatedSegment = repository.save(existingSegment);
        return mapper.toResponse(updatedSegment);
    }

    public List<LineSegmentResponse> getByLineId(Long lineId) {
        List<LineSegment> segments = repository.findByLine_IdOrderByOrder(lineId);
        return segments.stream()
                .map(mapper::toResponse)
                .toList();
    }
}
