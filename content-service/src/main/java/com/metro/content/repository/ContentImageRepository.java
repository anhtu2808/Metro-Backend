package com.metro.content.repository;

import com.metro.content.entity.Content;
import com.metro.content.entity.ContentImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentImageRepository extends JpaRepository<ContentImage, Long> {
}
