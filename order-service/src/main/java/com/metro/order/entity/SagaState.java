package com.metro.order.entity;

import com.metro.order.enums.SagaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    Long newEndStationId;
    @Enumerated(EnumType.STRING)
    SagaStatus status;  // PENDING, COMPLETED, FAILED
    @Column(length = 2048)
    String paymentUrl;  // For pending response
    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    LocalDateTime createAt;
}