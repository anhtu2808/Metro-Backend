package com.metro.order.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    BigDecimal price;;

}
