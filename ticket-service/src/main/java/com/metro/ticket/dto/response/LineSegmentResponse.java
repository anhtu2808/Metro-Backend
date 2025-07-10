package com.metro.ticket.dto.response;

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
    Long startStationId; // Thêm trường này
    Long endStationId;
}
