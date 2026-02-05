package com.example.accountservice.controller;

import com.example.accountservice.dto.request.MovementCreateRequestDTO;
import com.example.accountservice.dto.response.MovementResponseDTO;
import com.example.accountservice.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService service;

    @PostMapping
    public ResponseEntity<MovementResponseDTO> create(@Valid @RequestBody MovementCreateRequestDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }
}
