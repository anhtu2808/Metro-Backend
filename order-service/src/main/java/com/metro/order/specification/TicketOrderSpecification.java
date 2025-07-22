package com.metro.order.specification;

import com.metro.order.dto.request.TicketOrderFilterRequest;
import com.metro.order.entity.TicketOrder;
import com.metro.order.enums.TicketStatus;
import com.metro.order.exception.AppException;
import com.metro.order.exception.ErrorCode;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

public class TicketOrderSpecification {
    public static Specification<TicketOrder> withFilter(TicketOrderFilterRequest f, List<Long> ticketTypeIds) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (f.getUserId() != null)
                predicates.add(cb.equal(root.get("userId"), f.getUserId()));

            if (ticketTypeIds != null && !ticketTypeIds.isEmpty())
                predicates.add(root.get("ticketTypeId").in(ticketTypeIds));
            if(f.getStatus() != null)
                try {
                    TicketStatus status = TicketStatus.valueOf(f.getStatus().toUpperCase());
                    predicates.add(cb.equal(root.get("status"), f.getStatus()));
                } catch (IllegalArgumentException e) {
                    throw new AppException(ErrorCode.INVALID_TICKET_STATUS);
                }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<TicketOrder> withDateRange(LocalDateTime fromDate, LocalDateTime toDate) {
        return (root, query, cb) -> cb.and(
                cb.greaterThanOrEqualTo(root.get("createAt"), fromDate),
                cb.lessThanOrEqualTo(root.get("createAt"), toDate)
        );
    }
}
