package com.metro.route.controller;

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
    public StationResponse createStation(@Valid @RequestBody StationCreationRequest request) {
        return stationService.createStation(request);
    }

    @GetMapping("/{id}")
    public StationResponse getStationById(@PathVariable Long id) {
        return stationService.getStationById(id);
    }

    @GetMapping
    public PageResponse<StationResponse> getStations(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sort) {
        return stationService.getStations(page, size, sort);
    }

    @PutMapping("/{id}")
    public StationResponse updateStation(@PathVariable Long id, @Valid @RequestBody StationUpdateRequest request) {
        return stationService.updateStation(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
    }

}
