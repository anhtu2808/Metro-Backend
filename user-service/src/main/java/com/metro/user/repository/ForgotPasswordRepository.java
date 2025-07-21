package com.metro.user.repository;

import com.metro.user.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long> {
    Optional<ForgotPassword> findTopByEmailAndOtpCodeAndUsedFalseOrderByExpiredAtDesc(String email, String otpCode);
}
