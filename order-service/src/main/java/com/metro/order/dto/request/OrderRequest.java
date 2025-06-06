package com.metro.order.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    Long id;
    Long ticketTypeId;
    Long startStationId;
    Long endStationId;
    Long userId;
}
