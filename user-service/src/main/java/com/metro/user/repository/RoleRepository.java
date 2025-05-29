package com.metro.user.repository;

import com.metro.user.entity.Role;
import com.metro.user.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);
    boolean existsByName(RoleType name);

}
