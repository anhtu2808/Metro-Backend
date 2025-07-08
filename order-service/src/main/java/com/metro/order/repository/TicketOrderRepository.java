package com.metro.order.repository;

import com.metro.order.entity.TicketOrder;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    long countByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);
}
