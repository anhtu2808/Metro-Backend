package com.metro.route.dto.request.line;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineUpdateRequest {

    @Size(min = 2, max = 10, message = "Line code must be between 2 and 10 characters")
    String lineCode;

    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    String description;

    @Positive(message = "Start station ID must be a positive number")
    Long startStationId;

    @Positive(message = "Final station ID must be a positive number")
    Long finalStationId;
}