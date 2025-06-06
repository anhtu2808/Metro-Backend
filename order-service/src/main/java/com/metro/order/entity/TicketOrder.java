package com.metro.order.entity;
import com.metro.common_lib.entity.AbstractAuditingEntity;
import com.metro.order.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE ticket_order SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class TicketOrder extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String ticketCode;
    Long userId;
    Long transactionId;
    Long ticketTypeId;
    @Enumerated(EnumType.STRING)
    TicketStatus status;
    Long startStationId;
    Long endStationId;
    BigDecimal price;
    LocalDateTime purchaseDate;
    LocalDateTime validUntil;
}
