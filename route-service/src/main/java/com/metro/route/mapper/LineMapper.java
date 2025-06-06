package com.metro.route.mapper;

import com.metro.common_lib.mapper.DefaultConfigMapper;
import com.metro.common_lib.mapper.EntityMappers;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;
import org.mapstruct.Mapper;

@Mapper(
        config = DefaultConfigMapper.class
)
public interface LineMapper extends EntityMappers<Line, LineCreationRequest, LineUpdateRequest, LineResponse> {
}
