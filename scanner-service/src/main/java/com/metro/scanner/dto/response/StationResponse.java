package com.metro.scanner.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationResponse {
    Long id;
    String stationCode;
    String name;
    String imageUrl;
    String address;
    String latitude;
    String longitude;
}
