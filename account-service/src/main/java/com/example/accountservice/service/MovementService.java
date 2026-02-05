package com.example.accountservice.service;

import com.example.accountservice.domain.AccountEntity;
import com.example.accountservice.domain.MovementEntity;
import com.example.accountservice.dto.request.MovementCreateRequestDTO;
import com.example.accountservice.dto.response.MovementResponseDTO;
import com.example.accountservice.exception.BadRequestException;
import com.example.accountservice.exception.NotFoundException;
import com.example.accountservice.mapper.MovementMapper;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class MovementService {

    private final AccountRepository accountRepo;
    private final MovementRepository movementRepo;
    private final MovementMapper mapper;

    @Transactional
    public MovementResponseDTO create(MovementCreateRequestDTO dto) {
        AccountEntity account = accountRepo.findById(dto.getAccountId())
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));

        if (Boolean.FALSE.equals(account.getEstado())) {
            throw new BadRequestException("Cuenta inactiva");
        }

        BigDecimal valor = dto.getValor();
        String tipo = dto.getTipoMovimiento().toUpperCase();

        BigDecimal nuevoSaldo;
        if ("DEPOSITO".equals(tipo)) {
            nuevoSaldo = account.getSaldo().add(valor);
        } else if ("RETIRO".equals(tipo)) {
            nuevoSaldo = account.getSaldo().subtract(valor);
            if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
                throw new BadRequestException("Saldo no disponible");
            }
        } else {
            throw new BadRequestException("Tipo de movimiento invalido");
        }

        account.setSaldo(nuevoSaldo);

        MovementEntity movement = MovementEntity.builder()
                .account(account)
                .fecha(OffsetDateTime.now())
                .tipoMovimiento(tipo)
                .valor(valor)
                .saldoDisponible(nuevoSaldo)
                .build();

        // Guardamos movimiento y cuenta en misma transacción
        movementRepo.save(movement);
        accountRepo.save(account);

        return mapper.toResponse(movement);
    }
}
