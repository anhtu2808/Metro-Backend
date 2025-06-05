package com.metro.payment.dto.request;


import com.metro.payment.enums.PaymentStatusEnum;
import com.metro.payment.enums.PaymentMethodEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    Long id;
    PaymentMethodEnum paymentMethod;
    float amount;
    Long orderTicketId;
}
