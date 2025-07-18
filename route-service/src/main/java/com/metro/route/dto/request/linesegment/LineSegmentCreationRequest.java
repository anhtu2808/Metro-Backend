package com.metro.route.dto.request.linesegment;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineSegmentCreationRequest {

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 120, message = "Duration must not exceed 120 minutes")
    Integer duration;

    @NotNull(message = "Order is required")
    @Min(value = 0, message = "Order must be at least 0")
    @Max(value = 1000, message = "Order must not exceed 1000")
    Integer order;

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be positive")
    @DecimalMin(value = "0.1", message = "Distance must be at least 0.1 km")
    @DecimalMax(value = "50.0", message = "Distance must not exceed 50 km")
    Float distance;

    @NotNull(message = "Line ID is required")
    @Positive(message = "Line ID must be a positive number")
    Long lineId;

    @NotNull(message = "Start station ID is required")
    @Positive(message = "Start station ID must be a positive number")
    Long startStationId;

    @NotNull(message = "End station ID is required")
    @Positive(message = "End station ID must be a positive number")
    Long endStationId;
}
