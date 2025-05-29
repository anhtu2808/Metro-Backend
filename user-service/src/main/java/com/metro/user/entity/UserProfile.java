package com.metro.user.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class UserProfile extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    User user;
    String firstName;
    String lastName;
    String address;
    String phone;
    String avatarUrl;

}
