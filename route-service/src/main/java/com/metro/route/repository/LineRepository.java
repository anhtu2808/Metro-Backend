package com.metro.route.repository;

import com.metro.route.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line,Long> {
}
