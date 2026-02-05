package com.example.accountservice.service;

import com.example.accountservice.domain.AccountEntity;
import com.example.accountservice.dto.request.AccountCreateRequestDTO;
import com.example.accountservice.dto.request.AccountUpdateRequestDTO;
import com.example.accountservice.dto.response.AccountResponseDTO;
import com.example.accountservice.exception.BadRequestException;
import com.example.accountservice.exception.NotFoundException;
import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repo;
    private final AccountMapper mapper;

    @Transactional
    public AccountResponseDTO create(AccountCreateRequestDTO dto) {
        if (repo.existsByNumeroCuenta(dto.getNumeroCuenta())) {
            throw new BadRequestException("Numero de cuenta ya existe");
        }

        BigDecimal opening = dto.getSaldoInicial() == null ? BigDecimal.ZERO : dto.getSaldoInicial();

        AccountEntity entity = AccountEntity.builder()
                .numeroCuenta(dto.getNumeroCuenta())
                .tipoCuenta(dto.getTipoCuenta())
                .saldoInicial(opening)     // ✅ persistimos apertura
                .saldo(opening)            // ✅ saldo actual arranca igual
                .estado(dto.getEstado())
                .clienteId(dto.getClienteId())
                .build();

        return mapper.toResponse(repo.save(entity));
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO getById(Long id) {
        return mapper.toResponse(repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada")));
    }

    @Transactional(readOnly = true)
    public List<AccountResponseDTO> getAll() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public AccountResponseDTO update(Long id, AccountUpdateRequestDTO dto) {
        AccountEntity entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        mapper.updateFrom(entity, dto);

        // ❗ Nota: saldoInicial NO se toca en update (regla negocio)
        return mapper.toResponse(repo.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Cuenta no encontrada");
        repo.deleteById(id);
    }
}
