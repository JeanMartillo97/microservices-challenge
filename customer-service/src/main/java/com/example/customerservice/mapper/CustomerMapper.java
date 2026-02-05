package com.example.customerservice.mapper;

import com.example.customerservice.domain.CustomerEntity;
import com.example.customerservice.domain.PersonEntity;
import com.example.customerservice.dto.request.CustomerCreateRequestDTO;
import com.example.customerservice.dto.request.CustomerUpdateRequestDTO;
import com.example.customerservice.dto.response.CustomerResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    PersonEntity toPerson(CustomerCreateRequestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePersonFromDto(CustomerUpdateRequestDTO dto, @MappingTarget PersonEntity person);

    @Mapping(target = "clienteId", source = "id")
    @Mapping(target = "nombre", source = "person.nombre")
    @Mapping(target = "genero", source = "person.genero")
    @Mapping(target = "edad", source = "person.edad")
    @Mapping(target = "identificacion", source = "person.identificacion")
    @Mapping(target = "direccion", source = "person.direccion")
    @Mapping(target = "telefono", source = "person.telefono")
    CustomerResponseDTO toResponse(CustomerEntity entity);
}
