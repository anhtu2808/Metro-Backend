package com.metro.user.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import com.metro.user.enums.StudentVerificationStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SQLDelete(sql = "UPDATE student_verification SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class StudentVerification extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String schoolName;

    String imageUrl;

    LocalDate graduateDate;

    @Enumerated(EnumType.STRING)
    StudentVerificationStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
