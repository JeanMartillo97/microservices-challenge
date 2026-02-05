package com.example.accountservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountUpdateRequestDTO {

    @NotBlank
    @Size(max = 20)
    private String tipoCuenta;

    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal saldo;

    @NotNull
    private Boolean estado;
}
