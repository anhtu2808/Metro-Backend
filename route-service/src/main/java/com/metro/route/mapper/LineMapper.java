package com.metro.route.mapper;

import com.metro.common_lib.mapper.EntityMapper;
import com.metro.route.dto.request.LineRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;

public interface LineMapper extends EntityMapper <Line, LineRequest, LineResponse> {
}
