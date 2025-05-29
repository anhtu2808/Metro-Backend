package com.metro.user.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE user SET deleted = 1 WHERE id = ?")
public class User extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 100, unique = true)
    String username;
    @Column(length = 100, unique = true)
    String email;
    @Column(length = 100)
    String password;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserProfile profile;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

}
