package com.example.customerservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerCreateRequestDTO {

    @NotBlank @Size(max = 150)
    private String nombre;

    @NotBlank @Size(max = 20)
    private String genero;

    @NotNull @Min(0)
    private Integer edad;

    @NotBlank @Size(max = 50)
    private String identificacion;

    @NotBlank @Size(max = 200)
    private String direccion;

    @NotBlank @Size(max = 30)
    private String telefono;

    @NotBlank @Size(min = 4, max = 100)
    private String contrasena;

    @NotNull
    private Boolean estado;
}
