package com.metro.route.dto.response;

import com.metro.route.entity.Station;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusRouteResponse {
    Long id;
    String busCode;
    String startLocation;
    String endLocation;
    int headwayMinutes;
    Float distanceToStation;
    String busStationName;
}
