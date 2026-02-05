package com.example.accountservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_snapshot")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerSnapshotEntity {

    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "identificacion", nullable = false, length = 50)
    private String identificacion;

    @Column(name = "estado", nullable = false)
    private Boolean estado;
}
