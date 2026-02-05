package com.example.customerservice.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @Column(name = "cliente_id")
    private Long id;

    @MapsId
    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private PersonEntity person;

    @Column(name = "contrasena_hash", nullable = false, length = 200)
    private String contrasenaHash;

    @Column(nullable = false)
    private Boolean estado;
}
