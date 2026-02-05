package com.example.customerservice.service;

import com.example.customerservice.domain.*;
import com.example.customerservice.dto.request.CustomerRequestDTO;
import com.example.customerservice.dto.response.CustomerResponseDTO;
import com.example.customerservice.exception.BadRequestException;
import com.example.customerservice.exception.NotFoundException;
import com.example.customerservice.mapper.CustomerMapper;
import com.example.customerservice.repository.CustomerRepository;
import com.example.customerservice.repository.PersonRepository;
import com.example.customerservice.dto.request.CustomerCreateRequestDTO;
import com.example.customerservice.dto.request.CustomerUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PersonRepository personRepository;
    private final CustomerMapper mapper;

    @Transactional
    public CustomerResponseDTO create(CustomerCreateRequestDTO dto) {
        if (personRepository.existsByIdentificacion(dto.getIdentificacion())) {
            throw new BadRequestException("La identificacion ya existe");
        }

        PersonEntity person = mapper.toPerson(dto);

        CustomerEntity customer = CustomerEntity.builder()
                .person(person)
                .contrasenaHash(hash(dto.getContrasena()))
                .estado(dto.getEstado())
                .build();

        CustomerEntity saved = customerRepository.save(customer);
        return mapper.toResponse(saved);
    }


    @Transactional(readOnly = true)
    public CustomerResponseDTO getById(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return mapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> getAll() {
        return customerRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public CustomerResponseDTO update(Long id, CustomerUpdateRequestDTO dto) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));

        String nuevaIdent = dto.getIdentificacion();
        if (nuevaIdent != null && !nuevaIdent.equals(customer.getPerson().getIdentificacion())) {
            if (personRepository.existsByIdentificacion(nuevaIdent)) {
                throw new BadRequestException("La identificacion ya existe");
            }
        }

        mapper.updatePersonFromDto(dto, customer.getPerson());
        customer.setEstado(dto.getEstado());

        if (dto.getContrasena() != null && !dto.getContrasena().isBlank()) {
            customer.setContrasenaHash(hash(dto.getContrasena()));
        }

        CustomerEntity saved = customerRepository.save(customer);
        return mapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado");
        }
        customerRepository.deleteById(id);
    }

    private String hash(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("Error hashing password", e);
        }
    }
}
