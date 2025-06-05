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
public class LineSegmentUpdateRequest {

    @Positive(message = "Segment order must be positive")
    Integer order;

    @Positive(message = "Duration must be positive")
    Integer duration;

    @Positive(message = "Distance must be positive")
    Float distance;

    Long lineId;

    Long startStationId;

    Long endStationId;
}
