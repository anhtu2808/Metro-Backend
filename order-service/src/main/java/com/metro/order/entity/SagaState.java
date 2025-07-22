package com.metro.order.entity;

import com.metro.order.enums.SagaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class SagaState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long sagaId;
    Long ticketOrderId;
    BigDecimal adjustmentAmount;
    BigDecimal newPrice;  // New price after adjustment
    Long newEndStationId;
    @Enumerated(EnumType.STRING)
    SagaStatus status;  // PENDING, COMPLETED, FAILED
    @Column(length = 2048)
    String paymentUrl;  // For pending response
}