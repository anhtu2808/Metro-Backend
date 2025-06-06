package com.metro.route.entity;

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
@SQLDelete(sql = "UPDATE metro_line SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "metro_line")
public class Line extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "line_code", nullable = false)
    String lineCode;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "description", nullable = false)
    String description;

    @ManyToOne
    @JoinColumn(name = "start_station_id", nullable = false, foreignKey = @ForeignKey(name = "fk_line_start_station_id_foreign"))
    Station startStation;

    @ManyToOne
    @JoinColumn(name = "final_station_id", nullable = false, foreignKey = @ForeignKey(name = "fk_line_final_station_id"))
    Station finalStation;

}
