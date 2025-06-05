package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMapper;
import com.metro.route.dto.request.LineSegmentRequest;
import com.metro.route.dto.response.LineSegmentResponse;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LineSegment extends EntityMapper<LineSegment, LineSegmentRequest, LineSegmentResponse> {
}
