package com.metro.content.entity;

import com.metro.common_lib.entity.AbstractAuditingEntity;
import com.metro.content.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SQLDelete(sql = "UPDATE content SET deleted = 1 WHERE id = ?")
@SQLRestriction("deleted = 0")
public class Content extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    ContentType type;
    String title;
    @Column(columnDefinition = "TEXT")
    String body;
    String summary;
    String status;
    @Column(name = "publish_at")
    LocalDate publishAt;
    @Column(name = "user_id")
    Long userId;
    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ContentImage> images;
}
