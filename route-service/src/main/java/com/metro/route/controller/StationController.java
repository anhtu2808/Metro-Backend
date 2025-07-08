package com.metro.route.controller;

import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.service.AbstractService;
import com.metro.route.dto.request.station.StationCreationRequest;
import com.metro.route.dto.request.station.StationUpdateRequest;
import com.metro.route.dto.response.StationResponse;
import com.metro.route.dto.response.lineSegment.StartStationResponse;
import com.metro.route.entity.Station;
import com.metro.route.service.StationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationController extends AbstractController<
        Station,
        StationCreationRequest,
        StationUpdateRequest,
        StationResponse
        > {

    public StationController(AbstractService<Station, StationCreationRequest, StationUpdateRequest, StationResponse> service) {
        super(service);
    }


}
