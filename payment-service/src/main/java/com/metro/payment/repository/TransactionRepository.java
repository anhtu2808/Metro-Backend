package com.metro.payment.repository;

import com.metro.payment.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByTransactionCode(String transactionCode);
    Optional<Transaction> findByTransactionCode(String transactionCode);
    Page<Transaction> findByUserId(Long userId, Pageable pageable);
}
