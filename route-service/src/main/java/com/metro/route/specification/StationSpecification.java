package com.metro.route.specification;

import com.metro.route.entity.Station;
import org.springframework.data.jpa.domain.Specification;

public class StationSpecification {
    public static Specification<Station> search(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return null;
            }
            String like = "%" + search.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), like),
                    cb.like(cb.lower(root.get("address")), like)
            );
        };
    }
}
