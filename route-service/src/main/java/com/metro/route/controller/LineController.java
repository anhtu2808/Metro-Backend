package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.entity.Line;
import com.metro.route.service.LineService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final LineService lineService;

    public LineController(LineService service, LineService lineService) {
        super(service);
        this.lineService = lineService;
    }

    @GetMapping("/search/by-line-code")
    public ApiResponse<PageResponse<LineResponse>> findByLineCode(
          @RequestParam String lineCode,
          @RequestParam(defaultValue = "1") int page,
          @RequestParam(defaultValue = "10") int size,
          @RequestParam(defaultValue = "id") String sort
    ) {
        try {
            PageResponse<LineResponse> response = lineService.findByLineCode(lineCode, page, size, sort);
            return ApiResponse.<PageResponse<LineResponse>>builder()
                    .result(response)
                    .message("Lines found")
                    .code(200)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<PageResponse<LineResponse>>builder()
                    .result(null)
                    .code(500)
                    .message("Error finding lines: " + e.getMessage())
                    .build();
        }
    }
}
