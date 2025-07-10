package com.metro.content.repository;

import com.metro.content.entity.Content;
import com.metro.content.enums.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Page<Content> findByType(ContentType type, Pageable pageable);
}
