package com.metro.ticket.entity;
import com.metro.common_lib.entity.AbstractAuditingEntity;
import com.metro.ticket.entity.Enum.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "tickets")
public class Ticket extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "ticket_code", nullable = false)
    Long ticketCode;

    @Column(name = "user_id", nullable = false)
    Long userId;

    @Column(name = "payment_id", nullable = false)
    Long paymentId;

    @Column(name = "status", nullable = false)
    TicketStatus status;

    @Column(name = "start_station_id", nullable = false)
    Long startStationId;

    @Column(name = "end_station_id", nullable = false)
    Long endStationId;

    @Column(name = "price", nullable = false)
    Float price;

    @Column(name = "purchase_date", nullable = false)
    LocalDateTime purchaseDate;

    @Column(name = "valid_until", nullable = false)
    LocalDateTime validUntil;

    @Column(name = "is_static_type", nullable = false)
    Boolean isStaticType;

    @ManyToOne
    @JoinColumn(name = "static_type_id", referencedColumnName = "id")
    StaticType staticType;
}
