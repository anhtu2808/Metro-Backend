package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.entity.Line;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lines")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LineController extends AbstractController<
        Line,
        LineCreationRequest,
        LineUpdateRequest,
        LineResponse
        > {
    public LineController(AbstractService<Line, LineCreationRequest, LineUpdateRequest, LineResponse> service) {
        super(service);
    }
}
