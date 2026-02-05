package com.example.accountservice.repository;

import com.example.accountservice.domain.MovementEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface MovementRepository extends JpaRepository<MovementEntity, Long> {

    @Query("""
      select m from MovementEntity m
      join fetch m.account a
      where a.clienteId = :clienteId
        and m.fecha between :desde and :hasta
      order by m.fecha asc
    """)
    List<MovementEntity> findReport(Long clienteId, OffsetDateTime desde, OffsetDateTime hasta);

    @Query("""
      select m from MovementEntity m
      where m.account.id = :accountId
        and m.fecha < :fechaInicio
      order by m.fecha desc
    """)
    List<MovementEntity> findLastBefore(Long accountId, OffsetDateTime fechaInicio, Pageable pageable);
}
