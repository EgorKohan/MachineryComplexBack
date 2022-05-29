package com.bsuir.services.impl;

import com.bsuir.models.AgriculturalOperation;
import com.bsuir.repositories.AgriculturalOperationRepository;
import com.bsuir.services.AgriculturalOperationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agricultural operation with id " + id + " not found"));
    }

    @Override
    public AgriculturalOperation save(AgriculturalOperation operation) {
        checkDates(operation);
        return operationRepository.save(operation);
    }

    @Override
    public AgriculturalOperation update(AgriculturalOperation operation) {
        if(operation.getId() == null) throw new IllegalArgumentException("Operation ID cannot be null!");
        checkDates(operation);
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

    private void checkDates(AgriculturalOperation operation) {
        Date endDate = operation.getEndDate();
        Date startDate = operation.getStartDate();
        if (endDate.before(startDate) || endDate.equals(startDate))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date " + endDate + " must be greater than start date " + startDate);
    }

}
