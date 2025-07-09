package com.metro.order.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketOrderTokenBody {
    Long ticketOrderId;
    String ticketCode;
    LocalDateTime expirationTime;
}
