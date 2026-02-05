package com.example.accountservice.service;

import com.example.accountservice.dto.response.CustomerSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CustomerLookupService {

    private final RestClient customerRestClient;

    @Value("${app.customer-service.base-url:http://localhost:8081}")
    private String baseUrl;

    // TTL: 60 segundos (ajústalo)
    private static final long TTL_SECONDS = 60;

    private final Map<Long, CacheEntry> cache = new ConcurrentHashMap<>();

    public String getCustomerFullName(Long clienteId) {
        return getCustomerName(clienteId);
    }

    public String getCustomerName(Long clienteId) {
        CacheEntry entry = cache.get(clienteId);

        if (entry != null && !entry.isExpired()) {
            return entry.value();
        }

        CustomerSummaryDTO dto = customerRestClient.get()
                .uri(baseUrl + "/clientes/{id}", clienteId)
                .retrieve()
                .body(CustomerSummaryDTO.class);

        String value = dto != null ? dto.getNombre() : null;

        cache.put(clienteId, new CacheEntry(value, Instant.now().plusSeconds(TTL_SECONDS)));

        return value;
    }

    private record CacheEntry(String value, Instant expiresAt) {
        boolean isExpired() {
            return Instant.now().isAfter(expiresAt);
        }
    }
}
