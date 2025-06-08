package com.metro.route.dto.request.busRoute;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusRouteUpdateRequest {

    @Size(min = 2, max = 10, message = "Bus code must be between 2 and 10 characters")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Bus code must contain only uppercase letters, numbers, or hyphens")
    String busCode;

    @Min(value = 5, message = "Headway minutes must be at least 5 minutes")
    @Max(value = 120, message = "Headway minutes must not exceed 120 minutes")
    int headwayMinutes;

    @DecimalMin(value = "0.1", message = "Distance to station must be at least 0.1 km")
    @DecimalMax(value = "100.0", message = "Distance to station must not exceed 100 km")
    Float distanceToStation;

    @Size(min = 3, max = 100, message = "Bus station name must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ,-]+$", message = "Bus station name must contain only letters, numbers, spaces, commas, or hyphens")
    String busStationName;

    @NotNull(message = "Station ID is required")
    @Positive(message = "Station ID must be a positive number")
    Long stationId;

    @Size(min = 3, max = 100, message = "Start location must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ,-]+$", message = "Start location must contain only letters, numbers, spaces, commas, or hyphens")
    String startLocation;

    @Size(min = 3, max = 100, message = "End location must be between 3 and 100 characters")
    @Pattern(regexp = "^[A-Za-z0-9 ,-]+$", message = "End location must contain only letters, numbers, spaces, commas, or hyphens")
    String endLocation;
}
