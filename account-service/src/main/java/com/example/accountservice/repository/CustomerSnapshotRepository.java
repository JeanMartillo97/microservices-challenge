package com.example.accountservice.repository;

import com.example.accountservice.domain.CustomerSnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSnapshotRepository extends JpaRepository<CustomerSnapshotEntity, Long> {
}
