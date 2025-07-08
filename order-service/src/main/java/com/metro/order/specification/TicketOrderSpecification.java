package com.metro.order.specification;

import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.entity.TicketOrder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public class TicketOrderSpecification {
    public static Specification<TicketOrder> withFilter(TicketOrderFilterRequest f, List<Long> staticTypeIds) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.getUserId() != null)
                predicates.add(cb.equal(root.get("userId"), f.getUserId()));

            if (f.getIsStudent() != null)
                predicates.add(cb.equal(root.get("isStudent"), f.getIsStudent()));

            if (staticTypeIds != null && !staticTypeIds.isEmpty())
                predicates.add(root.get("ticketTypeId").in(staticTypeIds));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
