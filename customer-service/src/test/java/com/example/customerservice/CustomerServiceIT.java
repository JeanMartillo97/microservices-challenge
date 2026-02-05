package com.example.customerservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("it")
class CustomerServiceIT {

    @Test
    void contextLoads() {
        // Si el contexto no levanta, falla aquí.
    }
}
