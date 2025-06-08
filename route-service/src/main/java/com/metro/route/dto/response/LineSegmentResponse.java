package com.metro.route.dto.response;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineSegmentResponse {
    Long id;

    Integer order;

    Integer duration;     // in minutes

    Float distance;       // in kilometers


    String lineName;
    Long lineId;
    Long startStationId; // Thêm trường này
    Long endStationId;
}
