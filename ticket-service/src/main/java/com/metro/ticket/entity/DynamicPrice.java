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
@Table(name = "dynamic_price", uniqueConstraints = @UniqueConstraint(columnNames = {"startStationId", "endStationId", "lineId"}))
@SQLDelete(sql = "UPDATE dynamic_price SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class DynamicPrice extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long startStationId;
    Long endStationId;
    Long lineId;
    BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dynamic_price_master_id", nullable = false)
    DynamicPriceMaster dynamicPriceMaster;
}
