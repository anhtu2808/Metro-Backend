package com.metro.route.service;
import org.springframework.security.access.prepost.PreAuthorize;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.line.LineCreationRequest;
import com.metro.route.dto.request.line.LineUpdateRequest;
import com.metro.route.dto.response.LineResponse;

import java.util.List;

public interface LineService {

    @PreAuthorize("hasAuthority('LINE_CREATE')")
    LineResponse createLine(LineCreationRequest request);

    LineResponse getLineById(Long id);

    PageResponse<LineResponse> getLines(int page, int size, String sort);

    @PreAuthorize("hasAuthority('LINE_UPDATE')")
    LineResponse updateLine(Long id, LineUpdateRequest request);

    @PreAuthorize("hasAuthority('LINE_DELETE')")
    void deleteLine(Long id);

    PageResponse<LineResponse> getLinesByLineCode(String lineCode, int page, int size, String sort);

    @PreAuthorize("hasAuthority('LINE_UPDATE')")
    LineResponse addStationsToLine(Long lineId, List<Long> stationIds);
}