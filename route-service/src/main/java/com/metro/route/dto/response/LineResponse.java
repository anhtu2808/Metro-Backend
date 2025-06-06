package com.metro.route.dto.response;

import com.metro.route.entity.Station;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    Station startStation;
    Station finalStation;
}
