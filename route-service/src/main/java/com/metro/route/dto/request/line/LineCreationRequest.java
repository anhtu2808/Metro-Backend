package com.metro.route.dto.request.line;

import jakarta.validation.constraints.NotBlank;
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
public class LineCreationRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    String name;

    @NotBlank(message = "Line code is required")
    @Size(min = 2, max = 10, message = "Line code must be between 2 and 10 characters")
    String lineCode;
    Integer order;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    String description;

    @NotNull(message = "Start station ID is required")
    @Positive(message = "Start station ID must be a positive number")
    Long startStationId;

    @NotNull(message = "Final station ID is required")
    @Positive(message = "Final station ID must be a positive number")
    Long finalStationId;
}