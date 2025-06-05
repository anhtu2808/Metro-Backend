package com.metro.route.dto.request.line;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineUpdateRequest {
    @NotNull(message = "Line ID is required")
    Long id;

    String lineCode;

    String name;

    String description;

    Long startStationId;

    Long finalStationId;
}