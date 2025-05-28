package com.metro.route.mapper;

import com.metro.route.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationMapper extends JpaRepository<Station, Long> {
}
