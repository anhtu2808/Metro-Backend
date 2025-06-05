package com.metro.route.dto.request.lineSegment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineSegmentCreationRequest {
    @NotNull(message = "Segment order is required")
    @Positive(message = "Segment order must be positive")
    Integer order;

    @NotNull(message = "Duration is required")
    @Positive(message = "Duration must be positive")
    Integer duration;

    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be positive")
    Float distance;

    @NotNull(message = "Line ID is required")
    Long lineId;

    @NotNull(message = "Start station ID is required")
    Long startStationId;

    @NotNull(message = "End station ID is required")
    Long endStationId;
