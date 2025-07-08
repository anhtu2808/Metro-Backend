package com.metro.ticket.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicPriceMasterResponse {
    Long id;
    Long lineId;
    String startPrice;
    String pricePerKm;
    String startRange;
}
