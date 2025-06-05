package com.metro.route.dto.request.busRoute;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusRouteUpdateRequest {
    @NotNull(message = "Bus route ID is required")
    Long id;

    String busCode;

    String startLocation;

    String endLocation;

    @Positive(message = "Headway minutes must be positive")
    Integer headwayMinutes;

    @Positive(message = "Distance to station must be positive")
    Float distanceToStation;

    String busStationName;

    Long stationId;
}
