package com.metro.payment.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import com.metro.payment.enums.PaymentStatusEnum;
import com.metro.payment.enums.PaymentMethodEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Table(name = "transactions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE transactions SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Payment extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "user_id",nullable = false)
    Long userId;
    @Column(nullable = false)
    float amount;
    @Column(nullable = false, unique = true)
    String transactionCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    PaymentStatusEnum status;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method",nullable = false, length = 20)
    PaymentMethodEnum paymentMethod;
    @Column(name = "ticket_order_id", nullable = false)
    Long orderTicketId;
}

