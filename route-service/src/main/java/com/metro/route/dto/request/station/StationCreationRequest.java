package com.metro.route.dto.request.station;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.URL;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationCreationRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    String name;
    @NotBlank(message = "Station Code is required")
    String stationCode;

    @URL(message = "Image URL must be a valid URL")
    String imageUrl;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    String address;

    @NotBlank(message = "Latitude is required")
    @Pattern(regexp = "^-?([1-8]?[0-9]|[1-9]0)\\.{1}\\d{1,6}$", message = "Invalid latitude format")
    String latitude;

    @NotBlank(message = "Longitude is required")
    @Pattern(regexp = "^-?((1[0-7]|[1-9])?[0-9]|180)\\.{1}\\d{1,6}$", message = "Invalid longitude format")
    String longitude;
}