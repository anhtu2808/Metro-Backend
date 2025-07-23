package com.metro.order.repository;

import com.metro.common_lib.dto.response.PageResponse;
import com.metro.order.dto.response.TicketOrderResponse;
import com.metro.order.entity.TicketOrder;
import org.hibernate.query.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long> {
    long countByPurchaseDateBetween(LocalDateTime start, LocalDateTime end);
    Page<TicketOrder> findAllByUserId(Long userId, Pageable pageable);
    Page<TicketOrder> findAll(Specification<TicketOrder> spec, Pageable pageable);
    List<TicketOrder> findAll(Specification<TicketOrder> spec);

    @Query(value = """
            SELECT DATE(purchase_date) AS period, SUM(price) AS revenue
            FROM ticket_order
            WHERE purchase_date BETWEEN :fromDate AND :toDate
              AND status IN ('INACTIVE','ACTIVE','USING')
            GROUP BY DATE(purchase_date)
            ORDER BY DATE(purchase_date)
            """, nativeQuery = true)
    List<Object[]> getRevenueByDay(@Param("fromDate") LocalDateTime fromDate,
                                   @Param("toDate") LocalDateTime toDate);

    @Query(value = """
            SELECT DATE_FORMAT(purchase_date, '%Y-%m') AS period, SUM(price) AS revenue
            FROM ticket_order
            WHERE purchase_date BETWEEN :fromDate AND :toDate
              AND status IN ('INACTIVE','ACTIVE','USING')
            GROUP BY DATE_FORMAT(purchase_date, '%Y-%m')
            ORDER BY DATE_FORMAT(purchase_date, '%Y-%m')
            """, nativeQuery = true)
    List<Object[]> getRevenueByMonth(@Param("fromDate") LocalDateTime fromDate,
                                     @Param("toDate") LocalDateTime toDate);

    @Query(value = """
            SELECT YEAR(purchase_date) AS period, SUM(price) AS revenue
            FROM ticket_order
            WHERE purchase_date BETWEEN :fromDate AND :toDate
              AND status IN ('INACTIVE','ACTIVE','USING')
            GROUP BY YEAR(purchase_date)
            ORDER BY YEAR(purchase_date)
            """, nativeQuery = true)
    List<Object[]> getRevenueByYear(@Param("fromDate") LocalDateTime fromDate,
                                    @Param("toDate") LocalDateTime toDate);
    @Modifying
    @Query(value = "UPDATE ticket_order SET status = 'ACTIVE',valid_until = valid_until + INTERVAL 30 DAY " +
            "WHERE status = 'INACTIVE' " +
            "AND purchase_date IS NOT NULL " +
            "AND purchase_date <= NOW() - INTERVAL 30 DAY", nativeQuery = true)
    int activateTicketsAfter30Days();

    @Modifying
    @Query(value = """
    UPDATE ticket_order 
    SET status = 'EXPIRED'
    WHERE status = 'ACTIVE'
    AND valid_until IS NOT NULL
    AND valid_until < NOW()
""", nativeQuery = true)
    int expireTickets();
}
