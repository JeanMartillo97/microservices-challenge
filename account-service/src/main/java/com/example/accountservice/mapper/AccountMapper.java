package com.example.accountservice.mapper;

import com.example.accountservice.domain.AccountEntity;
import com.example.accountservice.dto.response.AccountResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponseDTO toResponse(AccountEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFrom(@MappingTarget AccountEntity entity, com.example.accountservice.dto.request.AccountUpdateRequestDTO dto);
}
