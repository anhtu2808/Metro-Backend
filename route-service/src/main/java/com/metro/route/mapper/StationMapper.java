package com.metro.route_service.mapper;

import com.metro.route_service.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationMapper extends JpaRepository<Station, Long> {
}
