package com.metro.order.repository;

import com.metro.order.entity.SagaState;
import com.metro.order.enums.SagaStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SagaStateRepository extends JpaRepository<SagaState,Long> {
     List<SagaState> findByStatusAndCreateAtBefore(SagaStatus status, LocalDateTime createAt);
     @Modifying
     @Transactional
     @Query("UPDATE SagaState s SET s.status = :newStatus WHERE s.status = :oldStatus AND s.createAt < :cutoff")
     int markStaleSagasAsFailed(@Param("oldStatus") SagaStatus oldStatus,
                                @Param("newStatus") SagaStatus newStatus,
                                @Param("cutoff") LocalDateTime cutoff);
}
