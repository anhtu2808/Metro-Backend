package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.lineSegment.LineSegmentCreationRequest;
import com.metro.route.dto.request.lineSegment.LineSegmentUpdateRequest;
import com.metro.route.dto.response.LineSegmentResponse;
import com.metro.route.entity.LineSegment;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LineSegmentMapper extends EntityMappers<LineSegment, LineSegmentCreationRequest, LineSegmentUpdateRequest, LineSegmentResponse> {
}
