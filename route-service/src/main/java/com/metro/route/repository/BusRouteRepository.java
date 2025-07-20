package com.metro.route.repository;

import com.metro.route.entity.BusRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute,Long>, JpaSpecificationExecutor<BusRoute> {
    Page<BusRoute> findAll(Specification<BusRoute> spec, Pageable pageable);
}
