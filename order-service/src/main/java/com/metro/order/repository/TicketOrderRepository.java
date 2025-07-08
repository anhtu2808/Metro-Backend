package com.metro.order.repository;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.entity.TicketOrder;
import org.hibernate.query.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    long countByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);

    Page<TicketOrder> findAll(Specification<TicketOrder> spec, Pageable pageable);
}
