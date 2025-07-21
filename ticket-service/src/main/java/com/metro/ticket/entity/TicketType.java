package com.metro.ticket.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE ticket_type SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class TicketType extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String name;
    String description;
    Integer validityDays;
    @Builder.Default
    Boolean isStudent = false;
    @Builder.Default
    Boolean isStatic = true;
    BigDecimal price;
}
