package com.metro.route.controller;


import com.metro.common_lib.controller.AbstractController;
import com.metro.common_lib.dto.response.ApiResponse;
import com.metro.common_lib.dto.response.PageResponse;
import com.metro.route.dto.request.busRoute.BusRouteUpdateRequest;
import com.metro.route.entity.BusRoute;
import com.metro.route.service.BusRouteService;
import com.metro.route.dto.request.busRoute.BusRouteCreationRequest;
import com.metro.route.dto.response.BusRouteResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus-routes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BusRouteController extends AbstractController<
        BusRoute,
        BusRouteCreationRequest,
        BusRouteUpdateRequest,
        BusRouteResponse> {
    public BusRouteController(final BusRouteService service) {
        super(service);
    }
}
