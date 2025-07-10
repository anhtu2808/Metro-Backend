package com.metro.ticket.repository;

import com.metro.ticket.entity.DynamicPriceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicPriceMasterRepository extends JpaRepository<DynamicPriceMaster, Long> {
    boolean existsByLineId(Long lineId);
    java.util.Optional<DynamicPriceMaster> findByLineId(Long lineId);
}
