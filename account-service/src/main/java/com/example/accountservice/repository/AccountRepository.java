package com.example.accountservice.repository;

import com.example.accountservice.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsByNumeroCuenta(String numeroCuenta);
    Optional<AccountEntity> findByNumeroCuenta(String numeroCuenta);
    List<AccountEntity> findByClienteId(Long clienteId);
}
