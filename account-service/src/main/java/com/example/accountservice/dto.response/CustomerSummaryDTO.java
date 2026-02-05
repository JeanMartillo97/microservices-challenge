package com.example.accountservice.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerSummaryDTO {
    private Long id;

    // si customer-service maneja nombres/apellidos:
    private String nombres;
    private String apellidos;

    // fallback por si también existe "nombre"
    private String nombre;

    public String nombreCompleto() {
        String n = (nombres != null && !nombres.isBlank()) ? nombres : nombre;
        String a = (apellidos != null) ? apellidos : "";
        return (n == null ? "" : n).trim() + (a.isBlank() ? "" : " " + a.trim());
    }
}
