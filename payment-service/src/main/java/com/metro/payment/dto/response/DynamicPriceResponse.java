package com.metro.payment.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicPriceResponse {
    Long lineId;
    Long startStationId;
    Long endStationId;
    BigDecimal price;
}
