package com.bsuir.services.impl;

import com.bsuir.models.AgriculturalOperation;
import com.bsuir.repositories.AgriculturalOperationRepository;
import com.bsuir.services.AgriculturalOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AgriculturalOperationServiceImpl implements AgriculturalOperationService {

	private final AgriculturalOperationRepository operationRepository;

	public AgriculturalOperationServiceImpl(AgriculturalOperationRepository operationRepository) {
		this.operationRepository = operationRepository;
	}

	@Override
	public List<AgriculturalOperation> findAll() {
		return operationRepository.findAll();
	}

	@Override
	public AgriculturalOperation findById(Long id) {
		return operationRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agricultural operation with id " + id + " not found"));
	}

	@Override
	public AgriculturalOperation save(AgriculturalOperation operation) {
		return operationRepository.save(operation);
	}

	@Override
	public AgriculturalOperation update(AgriculturalOperation operation) {
		return operationRepository.save(operation);
	}

	@Override
	public void delete(AgriculturalOperation operation) {
		operationRepository.delete(operation);
	}

	@Override
	public void deleteById(Long id) {
		operationRepository.deleteById(id);
	}
}
