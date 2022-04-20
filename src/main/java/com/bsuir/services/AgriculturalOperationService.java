package com.bsuir.services;

import com.bsuir.models.AgriculturalOperation;

import java.util.List;

public interface AgriculturalOperationService {

	List<AgriculturalOperation> findAll();

	AgriculturalOperation findById(Long id);

	AgriculturalOperation save(AgriculturalOperation operation);

	AgriculturalOperation update(AgriculturalOperation operation);

	void delete(AgriculturalOperation operation);

	void deleteById(Long id);
}
