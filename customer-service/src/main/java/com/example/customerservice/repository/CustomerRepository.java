package com.example.customerservice.repository;

import com.example.customerservice.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByPersonIdentificacion(String identificacion);
    boolean existsByPersonIdentificacion(String identificacion);
}
