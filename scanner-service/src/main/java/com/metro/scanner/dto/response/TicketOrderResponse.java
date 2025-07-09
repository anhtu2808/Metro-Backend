package com.metro.scanner.dto.response;


import com.metro.scanner.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketOrderResponse {
    Long id;
    String ticketCode;
    UserResponse user;
    Long transactionId;
    TicketStatus status;
    TicketTypeResponse ticketType;
    StationResponse startStation;
    StationResponse endStation;
    BigDecimal price;
    LocalDateTime purchaseDate;
    String ticketQRToken;
    LocalDateTime validUntil;
}