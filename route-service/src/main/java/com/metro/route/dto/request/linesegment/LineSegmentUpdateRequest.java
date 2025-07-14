package com.metro.route.dto.request.linesegment;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineSegmentUpdateRequest {

    @Positive(message = "Duration must be positive")
    Integer duration;

    @Min(value = 0, message = "Order must be at least 0")
    @Max(value = 1000, message = "Order must not exceed 1000")
    Integer order;

    @Positive(message = "Distance must be positive")
    @DecimalMin(value = "0.1", message = "Distance must be at least 0.1 km")
    @DecimalMax(value = "50.0", message = "Distance must not exceed 50 km")
    Float distance;

}
