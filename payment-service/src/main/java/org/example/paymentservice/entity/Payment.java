package org.example.paymentservice.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment extends AbstractAuditingEntity {



}
