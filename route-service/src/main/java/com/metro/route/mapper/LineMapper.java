package com.metro.route_service.mapper;

import com.metro.common_lib.mapper.EntityMapper;
import com.metro.route_service.dto.request.LineRequest;
import com.metro.route_service.dto.response.LineResponse;
import com.metro.route_service.entity.Line;

public interface LineMapper extends EntityMapper <Line, LineRequest, LineResponse> {
}
