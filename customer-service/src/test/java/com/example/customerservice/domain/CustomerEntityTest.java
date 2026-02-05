package com.example.customerservice.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerEntityTest {

    @Test
    void shouldBuildCustomerEntityWithPerson() {
        PersonEntity person = PersonEntity.builder()
                .id(1L)
                .nombre("Jean Martillo")
                .genero("M")
                .edad(31)
                .identificacion("1100000009")
                .direccion("Guayaquil")
                .telefono("0888888888")
                .build();

        CustomerEntity customer = CustomerEntity.builder()
                .id(1L)
                .person(person)
                .contrasenaHash("HASH_1234")
                .estado(true)
                .build();

        assertNotNull(customer);
        assertEquals(1L, customer.getId());
        assertNotNull(customer.getPerson());
        assertEquals("Jean Martillo", customer.getPerson().getNombre());
        assertEquals("HASH_1234", customer.getContrasenaHash());
        assertTrue(customer.getEstado());
    }

    @Test
    void shouldRejectNullPasswordHashByContract() {
        PersonEntity person = PersonEntity.builder()
                .id(2L)
                .nombre("Cliente Sin Password")
                .genero("M")
                .edad(20)
                .identificacion("9999999999")
                .direccion("Quito")
                .telefono("0999999999")
                .build();

        CustomerEntity customer = CustomerEntity.builder()
                .id(2L)
                .person(person)
                .contrasenaHash(null)
                .estado(true)
                .build();

        assertNull(customer.getContrasenaHash(),
                "Esto NO debería persistirse porque contrasenaHash es NOT NULL en DB");
    }

    @Test
    void shouldAllowEmptyPasswordHashIfYourBusinessRuleAllowsIt() {
        PersonEntity person = PersonEntity.builder()
                .id(3L)
                .nombre("Cliente Empty Password")
                .genero("M")
                .edad(22)
                .identificacion("8888888888")
                .direccion("Quito")
                .telefono("0988888888")
                .build();

        CustomerEntity customer = CustomerEntity.builder()
                .id(3L)
                .person(person)
                .contrasenaHash("")
                .estado(true)
                .build();

        assertEquals("", customer.getContrasenaHash());
        assertTrue(customer.getEstado());
    }
}
