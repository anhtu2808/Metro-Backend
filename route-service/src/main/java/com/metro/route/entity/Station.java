package com.metro.route.entity;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.metro.common_lib.entity.AbstractAuditingEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE station SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Table(name = "station", uniqueConstraints = {
        @UniqueConstraint(columnNames = "station_code")
})
public class Station extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "station_code", nullable = false)
    String stationCode;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "image_url")
    String imageUrl;

    @Column(name = "address",nullable = false)
    String address;

    @Column(name = "latitude", nullable = false)
    String latitude;

    @Column(name = "longitude", nullable = false)
    String longitude;
}
