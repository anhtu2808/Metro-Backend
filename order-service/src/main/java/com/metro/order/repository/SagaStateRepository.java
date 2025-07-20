package com.metro.order.repository;

import com.metro.order.entity.SagaState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaStateRepository extends JpaRepository<SagaState,Long> {
}
