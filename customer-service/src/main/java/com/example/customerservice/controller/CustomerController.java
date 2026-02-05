package com.example.customerservice.controller;

import com.example.customerservice.dto.request.CustomerCreateRequestDTO;
import com.example.customerservice.dto.request.CustomerUpdateRequestDTO;
import com.example.customerservice.dto.response.CustomerResponseDTO;
import com.example.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerCreateRequestDTO request) {
        return ResponseEntity.status(201).body(customerService.create(request));
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getById(@PathVariable Long id) {
        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerResponseDTO> getAll() {
        return customerService.getAll();
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequestDTO request) {
        return customerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
