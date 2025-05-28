package com.metro.route.repository;

import com.metro.route.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
}
