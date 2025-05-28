package com.metro.route.entity;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.metro.common_lib.entity.AbstractAuditingEntity;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Station extends AbstractAuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
