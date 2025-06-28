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
import java.util.Optional;

@Repository
public interface LineSegmentRepository extends JpaRepository<LineSegment, Long> {
    List<LineSegment> findByLine_IdOrderByOrder(Long lineId);
    Page<LineSegment> findByLineId(Long lineCode, Pageable pageable);
    List<LineSegment> findByLineIdAndOrderBetween(Long lineId, Integer startOrder, Integer endOrder);
    LineSegment findByLineIdAndStartStationId(Long lineId,Long startStationId);
    @Query("SELECT ls.line.id FROM LineSegment ls " +
            "WHERE (ls.startStation.id = :start AND ls.endStation.id = :end) " +
            "   OR (ls.startStation.id = :end AND ls.endStation.id = :start)")
    Optional<Long> findLineIdByStartAndEndStations(@Param("start") Long startStationId,
                                                   @Param("end") Long endStationId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LineSegment  WHERE line.id = :lineId")
    void deleteAllByLine_Id(Long lineId);


}
