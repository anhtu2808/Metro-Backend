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
@SQLDelete(sql = "UPDATE line_segment SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "line_segment")
public class LineSegment extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "segment_order", nullable = false)
    Integer order;

    @Column(name = "duration", nullable = false)
    @Builder.Default
    Integer duration = 0;

    @Column(name = "distance", nullable = false)
    @Builder.Default
    Float distance = 0.0f;

    @ManyToOne
    @JoinColumn(name = "line_id", nullable = false, foreignKey = @ForeignKey(name = "line_segment_line_id_foreign"))
    Line line;

    @ManyToOne
    @JoinColumn(name = "start_station_id", nullable = false, foreignKey = @ForeignKey(name = "fk_line_segment_start_station"))
    Station startStation;

    @ManyToOne
    @JoinColumn(name = "end_station_id", nullable = false, foreignKey = @ForeignKey(name = "fk_line_segment_end_station"))
    Station endStation;
}
