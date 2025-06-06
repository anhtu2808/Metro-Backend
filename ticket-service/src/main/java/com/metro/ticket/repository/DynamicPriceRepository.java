package com.metro.ticket.repository;

import com.metro.ticket.entity.DynamicPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicPriceRepository extends JpaRepository<DynamicPrice, Long> {

}
