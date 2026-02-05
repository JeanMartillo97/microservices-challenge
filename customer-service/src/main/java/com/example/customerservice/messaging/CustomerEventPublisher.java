package com.example.customerservice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
@RequiredArgsConstructor
public class CustomerEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishCreated(Long id, String nombre, String identificacion, boolean estado) {
        publish(CustomerEventType.CREATED, RabbitConfig.RK_CREATED, id, nombre, identificacion, estado);
    }

    public void publishUpdated(Long id, String nombre, String identificacion, boolean estado) {
        publish(CustomerEventType.UPDATED, RabbitConfig.RK_UPDATED, id, nombre, identificacion, estado);
    }

    public void publishDeleted(Long id) {
        CustomerEvent event = CustomerEvent.builder()
                .type(CustomerEventType.DELETED)
                .customerId(id)
                .occurredAt(OffsetDateTime.now())
                .build();

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.RK_DELETED, event);
    }

    private void publish(CustomerEventType type, String routingKey, Long id, String nombre, String identificacion, boolean estado) {
        CustomerEvent event = CustomerEvent.builder()
                .type(type)
                .customerId(id)
                .nombre(nombre)
                .identificacion(identificacion)
                .estado(estado)
                .occurredAt(OffsetDateTime.now())
                .build();

        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, routingKey, event);
    }
}
