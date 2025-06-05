package com.metro.route.entity;
import java.util.Set;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE bus_routes SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "bus_routes")
public class BusRoute extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "bus_code", nullable = false)
    String busCode;

    @Column(name = "start_location", nullable = false)
    String startLocation;

    @Column(name = "end_location", nullable = false)
    String endLocation;

    @Column(name = "headway_minutes", nullable = false)
    int headwayMinutes;

    @Column(name = "distance_to_station", nullable = false)
    Float distanceToStation;

    @Column(name = "bus_station_name", nullable = false)
    String busStationName;
    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false, foreignKey = @ForeignKey(name = "station_id_foreign"))
    Station station;
}
