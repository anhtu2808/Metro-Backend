package com.metro.payment.dto.response;


import com.metro.payment.enums.PaymentStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Long id;
    String transactionCode;
    PaymentStatusEnum paymentStatus;
    Long orderTicketId;
    float amount;
}
