package com.metro.ticket.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "dynamic_price_masters")
public class DynamicPriceMaster extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "start_price", nullable = false)
    Float startPrice;

    @Column(name = "price_per_km", nullable = false)
    Float pricePerKm;

    @Column(name = "start_range", nullable = false)
    Float startRange;

    @Column(name = "line_id", nullable = false)
    Long lineId;

}
