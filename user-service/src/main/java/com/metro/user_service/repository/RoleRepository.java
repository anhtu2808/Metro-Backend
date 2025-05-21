package com.metro.user_service.repository;

import com.metro.user_service.entity.Role;
import com.metro.user_service.entity.User;
import com.metro.user_service.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);

}
