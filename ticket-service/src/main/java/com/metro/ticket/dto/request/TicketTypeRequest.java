package com.metro.ticket.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketTypeRequest {
    Long id;
    String name;
    String description;
    Integer validityDays;
    Boolean isStudent = false;
    boolean isStatic = true;
    BigDecimal price;
}
