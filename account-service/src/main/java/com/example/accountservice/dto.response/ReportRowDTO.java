package com.example.accountservice.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ReportRowDTO {

    private String fecha;

    private Long clienteId;
    private String clienteNombre;

    private String numeroCuenta;
    private String tipoCuenta;
    private Boolean estado;

    private BigDecimal saldoInicial;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}
