package com.metro.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metro.user.entity.StudentVerification;

@Repository
public interface StudentVerificationRepository extends JpaRepository<StudentVerification, Long> {}
