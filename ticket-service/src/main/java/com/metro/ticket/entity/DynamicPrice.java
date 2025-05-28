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
public class DynamicPrice extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "start_station_id", nullable = false)
    Long startStationId;

    @Column(name = "end_station_id", nullable = false)
    Long endStationId;

    @Column(name = "line_id", nullable = false)
    Long lineId;

    @Column(name = "price", nullable = false)
    Float price;
}
