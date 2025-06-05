package com.metro.route.dto.request.busRoute;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusRouteCreationRequest {
    @NotBlank(message = "Bus code is required")
    String busCode;

    @NotBlank(message = "Start location is required")
    String startLocation;

    @NotBlank(message = "End location is required")
    String endLocation;

    @Positive(message = "Headway minutes must be positive")
    int headwayMinutes;

    @Positive(message = "Distance to station must be positive")
    Float distanceToStation;

    @NotBlank(message = "Bus station name is required")
    String busStationName;

    @NotNull(message = "Station ID is required")
    Long stationId;
}

