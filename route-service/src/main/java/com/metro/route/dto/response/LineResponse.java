package com.metro.route.dto.response;

import com.metro.route.dto.response.lineSegment.LineSegmentResponse;
import com.metro.route.entity.Station;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineResponse {
    Long id;
    String lineCode;
    String name;
    String description;
    StationResponse startStation;
    StationResponse finalStation;
    String startStationId;
    String finalStationId;
    List<LineSegmentResponse> lineSegments;
}
