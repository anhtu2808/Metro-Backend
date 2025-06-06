package com.metro.scanner.repository;

import com.metro.scanner.entity.Scanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScannerRepository extends JpaRepository<Scanner, Long> {
}
