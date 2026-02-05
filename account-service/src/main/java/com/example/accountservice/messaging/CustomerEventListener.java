package com.example.accountservice.messaging;

import com.example.accountservice.domain.CustomerSnapshotEntity;
import com.example.accountservice.repository.CustomerSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerSnapshotRepository repo;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    @Transactional
    public void onMessage(CustomerEvent event) {
        if (event == null || event.getCustomerId() == null) return;

        if (event.getType() == CustomerEventType.DELETED) {
            repo.deleteById(event.getCustomerId());
            return;
        }

        // CREATED / UPDATED
        CustomerSnapshotEntity snap = CustomerSnapshotEntity.builder()
                .customerId(event.getCustomerId())
                .nombre(event.getNombre() != null ? event.getNombre() : "N/A")
                .identificacion(event.getIdentificacion() != null ? event.getIdentificacion() : "N/A")
                .estado(event.getEstado() != null ? event.getEstado() : Boolean.TRUE)
                .build();

        repo.save(snap);
    }
}
