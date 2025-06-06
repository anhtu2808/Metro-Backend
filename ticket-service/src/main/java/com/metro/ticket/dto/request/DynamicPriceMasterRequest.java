package com.metro.ticket.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicPriceMasterRequest {
    Long lineId;
    String startPrice;
    String pricePerKm;
    String startRange;
}
