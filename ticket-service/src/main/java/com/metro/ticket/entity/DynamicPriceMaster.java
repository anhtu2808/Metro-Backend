package com.metro.ticket.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE dynamic_price_master SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class DynamicPriceMaster extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal startPrice;
    BigDecimal pricePerKm;
    BigDecimal startRange;
    Long lineId;
    @OneToMany(mappedBy = "dynamicPriceMaster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<DynamicPrice> dynamicPrices;
}
