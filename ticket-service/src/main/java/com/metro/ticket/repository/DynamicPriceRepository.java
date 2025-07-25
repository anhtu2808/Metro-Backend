package com.metro.ticket.repository;

import com.metro.ticket.entity.DynamicPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DynamicPriceRepository extends JpaRepository<DynamicPrice, Long> {

    List<DynamicPrice> findByLineId(Long lineId);


    @Modifying
    @Transactional
    @Query("DELETE FROM DynamicPrice  WHERE lineId = :lineId")
    void deleteByLineId(@Param("lineId") Long lineId);

    List<DynamicPrice> findByLineIdAndStartStationId(Long lineId, Long startStationId);

    Optional<DynamicPrice> findByLineIdAndStartStationIdAndEndStationId(Long lineId, Long startStationId, Long endStationId);

}
