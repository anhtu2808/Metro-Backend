package com.metro.route_service.repository;

import com.metro.route_service.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line,Long> {
}
