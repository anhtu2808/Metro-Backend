package com.metro.scanner.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Scanner extends AbstractAuditingEntity {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
