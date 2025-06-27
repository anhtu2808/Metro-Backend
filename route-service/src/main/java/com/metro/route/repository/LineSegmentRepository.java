package com.metro.route.repository;

import com.metro.route.entity.Line;
import com.metro.route.entity.LineSegment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LineSegmentRepository extends JpaRepository<LineSegment, Long> {
    List<LineSegment> findByLine_IdOrderByOrder(Long lineId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LineSegment  WHERE line.id = :lineId")
    void deleteAllByLine_Id(Long lineId);


}
