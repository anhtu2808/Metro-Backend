package com.metro.route.repository;

import com.metro.route.entity.LineSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineSegmentRepository extends JpaRepository<LineSegment, Long> {
    java.util.List<LineSegment> findByLine_IdOrderByOrder(Long lineId);
}
