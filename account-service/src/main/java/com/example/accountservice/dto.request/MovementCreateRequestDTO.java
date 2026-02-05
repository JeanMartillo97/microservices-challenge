package com.example.accountservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MovementCreateRequestDTO {

    @NotNull
    private Long accountId;

    @NotBlank
    @Size(max = 20)
    private String tipoMovimiento; // DEPOSITO / RETIRO

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal valor;
}
