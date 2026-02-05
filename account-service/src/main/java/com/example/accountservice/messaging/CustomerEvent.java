package com.example.accountservice.messaging;

import lombok.*;

import java.time.OffsetDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerEvent {
    private CustomerEventType type;
    private Long customerId;
    private String nombre;
    private String identificacion;
    private Boolean estado;
    private OffsetDateTime occurredAt;
}
