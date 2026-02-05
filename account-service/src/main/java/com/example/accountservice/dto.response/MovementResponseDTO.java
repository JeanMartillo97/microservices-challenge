package com.example.accountservice.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MovementResponseDTO {

    private Long id;
    private Long accountId;
    private OffsetDateTime fecha;
    private String tipoMovimiento;
    private BigDecimal valor;
    private BigDecimal saldoDisponible;
}
