package com.example.accountservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountCreateRequestDTO {

    @NotBlank
    @Size(max = 30)
    private String numeroCuenta;

    @NotBlank
    @Size(max = 20)
    private String tipoCuenta;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal saldoInicial;

    @NotNull
    private Boolean estado;

    @NotNull
    private Long clienteId;
}
