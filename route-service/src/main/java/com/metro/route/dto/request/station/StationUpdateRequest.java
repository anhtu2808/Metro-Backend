package com.metro.route.dto.request.station;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationUpdateRequest {
    @NotNull(message = "Station ID is required")
    Long id;

    String stationCode;

    String name;

    String imageUrl;

    String address;

    String latitude;

    String longitude;
}