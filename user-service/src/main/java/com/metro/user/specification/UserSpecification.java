package com.metro.user.specification;

import com.metro.user.dto.request.user.UserFilterRequest;
import com.metro.user.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> withFilter(UserFilterRequest f) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (f.getRole() != null) {
                predicates.add(cb.equal(root.get("role").get("name"), f.getRole()));
            }
            if (f.getDeleted() != null) {
                predicates.add(cb.equal(root.get("deleted"), f.getDeleted()));
            }
            if (f.getUsername() != null) {
                predicates.add(cb.equal(root.get("username"), f.getUsername()));
            }
            if (f.getEmail() != null) {
                predicates.add(cb.equal(root.get("email"), f.getEmail()));
            }
            if (f.getSearch() != null && !f.getSearch().isBlank()) {
                String pattern = "%" + f.getSearch().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("username")), pattern),
                        cb.like(cb.lower(root.get("email")), pattern),
                        cb.like(cb.lower(root.get("firstName")), pattern),
                        cb.like(cb.lower(root.get("lastName")), pattern)
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
