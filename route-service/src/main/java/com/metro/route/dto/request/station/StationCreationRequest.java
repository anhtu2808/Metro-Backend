package com.metro.route.dto.request.station;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationCreationRequest {
    @NotBlank(message = "Station code is required")
    String stationCode;

    @NotBlank(message = "Name is required")
    String name;

    String imageUrl;

    @NotBlank(message = "Address is required")
    String address;

    @NotBlank(message = "Latitude is required")
    String latitude;

    @NotBlank(message = "Longitude is required")
    String longitude;
}