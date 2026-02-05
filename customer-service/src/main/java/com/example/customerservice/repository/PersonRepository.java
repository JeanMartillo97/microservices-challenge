package com.example.customerservice.repository;

import com.example.customerservice.domain.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    boolean existsByIdentificacion(String identificacion);
}
