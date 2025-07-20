package com.metro.route.specification;

import com.metro.route.entity.BusRoute;
import org.springframework.data.jpa.domain.Specification;

public class BusRouteSpecification {
    public static Specification<BusRoute> stationSpec(Long stationId) {
        return (root, query, cb) -> stationId == null ? null : cb.equal(root.get("station").get("id"), stationId);
    }
}
