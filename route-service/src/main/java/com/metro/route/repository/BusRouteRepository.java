package com.metro.route_service.repository;

import com.metro.route_service.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
}
