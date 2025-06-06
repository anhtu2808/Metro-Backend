package com.metro.user.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE user SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
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
    @Builder.Default
    boolean isStudentVerified = false;
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;
    String firstName;
    String lastName;
    String address;
    String phone;
    String avatarUrl;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<StudentVerification> studentVerifications;
}
