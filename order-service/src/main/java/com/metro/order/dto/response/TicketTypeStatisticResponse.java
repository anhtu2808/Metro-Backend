package com.metro.order.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketTypeStatisticResponse {
    Long ticketTypeId;
    String name;
    Boolean isStatic;
    Boolean isStudent;
    Long ticketCount;
    BigDecimal revenue;
}
