package com.example.accountservice.controller;

import com.example.accountservice.dto.request.AccountCreateRequestDTO;
import com.example.accountservice.dto.request.AccountUpdateRequestDTO;
import com.example.accountservice.dto.response.AccountResponseDTO;
import com.example.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody AccountCreateRequestDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping("/{id}")
    public AccountResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<AccountResponseDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public AccountResponseDTO update(@PathVariable Long id, @Valid @RequestBody AccountUpdateRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
