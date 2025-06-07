package com.metro.route.dto.request.station;

import jakarta.validation.constraints.NotNull;
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
public class StationUpdateRequest {

    String stationCode;
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    String name;

    @URL(message = "Image URL must be a valid URL")
    @Size(max = 2048, message = "Image URL must not exceed 2048 characters")
    String imageUrl;
    @Size(min = 1, max = 500, message = "Address must be between 1 and 500 characters")
    String address;
    @Pattern(regexp = "^-?([1-8]?[0-9]|[1-9]0)\\.{1}\\d{1,6}$", message = "Invalid latitude format")
    String latitude;
    @Pattern(regexp = "^-?((1[0-7]|[1-9])?[0-9]|180)\\.{1}\\d{1,6}$", message = "Invalid longitude format")
    String longitude;
}