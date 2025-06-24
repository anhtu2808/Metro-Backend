package com.metro.route.repository;

import com.metro.route.entity.BusRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
    Page<BusRoute> findByStationId(Long stationId, Pageable pageable);
}
