package com.metro.ticket.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicPriceMasterRequest {
    @NotNull(message = "Line ID is required")
    Long lineId;
    @NotNull(message = "Start price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Start price must be greater than 0")
    BigDecimal startPrice;
    @NotNull(message = "Price per km is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price per km must be greater than 0")
    BigDecimal pricePerKm;
    @NotNull(message = "Start range is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Start range must be greater than 0")
    BigDecimal startRange;
}
