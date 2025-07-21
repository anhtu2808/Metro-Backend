package com.metro.payment.dto.response;

import com.metro.payment.enums.PaymentMethodEnum;
import com.metro.payment.enums.PaymentStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {
    Long id;
    Long userId;
    BigDecimal amount;
    String transactionCode;
    PaymentStatusEnum status;
    PaymentMethodEnum paymentMethod;
    Long orderTicketId;
}
