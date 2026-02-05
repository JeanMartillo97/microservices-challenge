package com.example.customerservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("it")
class CustomerControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void cleanup() {
        // ✅ Limpieza robusta: borra solo si la tabla existe.
        // Y además respeta FK (customer -> person).
        jdbcTemplate.execute("""
            DO $$
            BEGIN
                IF to_regclass('public.customer_snapshot') IS NOT NULL THEN
                    EXECUTE 'TRUNCATE TABLE customer_snapshot RESTART IDENTITY CASCADE';
                END IF;

                IF to_regclass('public.customer') IS NOT NULL THEN
                    EXECUTE 'TRUNCATE TABLE customer RESTART IDENTITY CASCADE';
                END IF;

                IF to_regclass('public.person') IS NOT NULL THEN
                    EXECUTE 'TRUNCATE TABLE person RESTART IDENTITY CASCADE';
                END IF;
            END
            $$;
        """);
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        String body = """
        {
          "nombre": "Jean Martillo",
          "genero": "M",
          "edad": 31,
          "identificacion": "1100000009",
          "direccion": "Guayaquil",
          "telefono": "0888888888",
          "contrasena": "1234",
          "estado": true
        }
        """;

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
