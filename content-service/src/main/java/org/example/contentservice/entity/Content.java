package org.example.contentservice.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Content extends AbstractAuditingEntity {
}
