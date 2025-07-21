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
    String latitude;
    String longitude;
}