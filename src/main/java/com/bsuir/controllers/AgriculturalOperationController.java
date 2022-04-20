package com.bsuir.controllers;

import com.bsuir.dtos.AgriculturalOperationDto;
import com.bsuir.mappers.AgriculturalOperationMapper;
import com.bsuir.models.AgriculturalOperation;
import com.bsuir.services.AgriculturalOperationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/operations")
public class AgriculturalOperationController {

	private final AgriculturalOperationMapper operationMapper;
	private final AgriculturalOperationService operationService;

	public AgriculturalOperationController(AgriculturalOperationMapper operationMapper,
										   AgriculturalOperationService operationService) {
		this.operationMapper = operationMapper;
		this.operationService = operationService;
	}

	@GetMapping
	public List<AgriculturalOperationDto> findAll() {
		List<AgriculturalOperation> operations = operationService.findAll();
		return operationMapper.toDtos(operations);
	}

	@GetMapping("/{id}")
	public AgriculturalOperationDto findById(@PathVariable("id") Long id) {
		AgriculturalOperation operation = operationService.findById(id);
		return operationMapper.toDto(operation);
	}

	@PostMapping
	public AgriculturalOperationDto create(@Valid @RequestBody AgriculturalOperationDto dto) {
		AgriculturalOperation operation = operationMapper.toOperation(dto);
		AgriculturalOperation savedOperation = operationService.save(operation);
		return operationMapper.toDto(savedOperation);
	}

	@PutMapping("/{id}")
	public AgriculturalOperationDto update(
			@Valid @RequestBody AgriculturalOperationDto dto,
			@PathVariable("id") Long id
	) {
		dto.setId(id);
		AgriculturalOperation operation = operationMapper.toOperation(dto);
		AgriculturalOperation updatedOperation = operationService.update(operation);
		return operationMapper.toDto(updatedOperation);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		operationService.deleteById(id);
	}
}
