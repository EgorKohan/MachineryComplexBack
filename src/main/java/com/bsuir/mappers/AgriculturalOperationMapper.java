package com.bsuir.mappers;

import com.bsuir.dtos.AgriculturalOperationDto;
import com.bsuir.models.AgriculturalOperation;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AgriculturalOperationMapper {
	AgriculturalOperation toOperation(AgriculturalOperationDto dto);

	AgriculturalOperationDto toDto(AgriculturalOperation operation);

	List<AgriculturalOperationDto> toDtos(Collection<AgriculturalOperation> operations);

	List<AgriculturalOperation> toOperations(Collection<AgriculturalOperationDto> dtos);
}
