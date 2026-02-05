package com.example.accountservice.mapper;

import com.example.accountservice.domain.MovementEntity;
import com.example.accountservice.dto.response.MovementResponseDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MovementMapper {

    @Mapping(target = "accountId", source = "account.id")
    MovementResponseDTO toResponse(MovementEntity entity);
}
