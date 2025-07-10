package com.metro.route.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicPriceResponse {
    Long id;
    Long startStationId;
    Long endStationId;
    Long lineId;
    BigDecimal price;
}
