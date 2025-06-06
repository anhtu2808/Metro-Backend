package com.metro.ticket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketTypeResponse {
    Long id;
    String name;
    String description;
    Integer validityDays;
    @JsonProperty("isStudent")
    Boolean isStudent = false;
    @JsonProperty("isStatic")
    boolean isStatic = true;
    BigDecimal price;
}
