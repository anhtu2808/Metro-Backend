package com.metro.route.controller;

import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.dto.response.lineSegment.EndStationResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.service.StationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationController {
    StationService stationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StationResponse> createStation(@Valid @RequestBody StationCreationRequest request) {
        return ApiResponse.<StationResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Station created successfully")
                .result(stationService.createStation(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<StationResponse> getStationById(@PathVariable Long id) {
        return ApiResponse.<StationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Station fetched successfully")
                .result(stationService.getStationById(id))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse<StationResponse>> getStations(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "id") String sort
    ) {
        PageResponse<StationResponse> pageResp = stationService.getStations(page, size, sort);
        return ApiResponse.<PageResponse<StationResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Stations fetched successfully")
                .result(pageResp)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<StationResponse> updateStation(
            @PathVariable Long id,
            @Valid @RequestBody StationUpdateRequest request
    ) {
        return ApiResponse.<StationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Station updated successfully")
                .result(stationService.updateStation(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.NO_CONTENT.value())
                .message("Station deleted successfully")
                .build();
    }
}
