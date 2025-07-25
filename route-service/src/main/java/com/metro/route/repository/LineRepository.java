package com.metro.route.repository;

import com.metro.route.entity.Line;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line,Long> {
    Page<Line> findByLineCode(String lineCode, Pageable pageable);
}
