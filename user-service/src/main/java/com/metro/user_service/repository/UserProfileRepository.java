package com.metro.user_service.repository;

import com.metro.user_service.entity.User;
import com.metro.user_service.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
