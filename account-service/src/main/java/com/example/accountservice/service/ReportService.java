package com.example.accountservice.service;

import com.example.accountservice.domain.AccountEntity;
import com.example.accountservice.domain.MovementEntity;
import com.example.accountservice.dto.response.ReportRowDTO;
import com.example.accountservice.repository.MovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final MovementRepository movementRepo;
    private final CustomerLookupService customerLookupService;

    private static final DateTimeFormatter SHORT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    public List<ReportRowDTO> getReport(Long clienteId, OffsetDateTime fechaInicio, OffsetDateTime fechaFin) {

        List<MovementEntity> movimientos = movementRepo.findReport(clienteId, fechaInicio, fechaFin);

        Map<Long, BigDecimal> saldoInicialPorCuenta = new HashMap<>();

        String clienteNombre = customerLookupService.getCustomerName(clienteId);

        List<ReportRowDTO> out = new ArrayList<>();

        for (MovementEntity m : movimientos) {
            AccountEntity a = m.getAccount();
            Long accountId = a.getId();

            BigDecimal saldoInicial = saldoInicialPorCuenta.computeIfAbsent(accountId, id -> {
                List<MovementEntity> lastList = movementRepo.findLastBefore(id, fechaInicio, PageRequest.of(0, 1));
                if (!lastList.isEmpty()) {
                    return lastList.get(0).getSaldoDisponible();
                }
                return a.getSaldoInicial();
            });

            out.add(ReportRowDTO.builder()
                    .fecha(m.getFecha().format(SHORT_FMT)) // ✅ corto
                    .clienteId(a.getClienteId())
                    .clienteNombre(clienteNombre)          // ✅ nuevo
                    .numeroCuenta(a.getNumeroCuenta())
                    .tipoCuenta(a.getTipoCuenta())
                    .estado(a.getEstado())
                    .saldoInicial(saldoInicial)
                    .movimiento(m.getValor())
                    .saldoDisponible(m.getSaldoDisponible())
                    .build());
        }

        return out;
    }
}
