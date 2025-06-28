package com.metro.payment.dto.request;


import com.metro.payment.enums.PaymentStatusEnum;
import com.metro.payment.enums.PaymentMethodEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    Long ticketOrderId;
    PaymentMethodEnum paymentMethod;
    String bankCode;
}
