package com.bsuir.services.impl;

import com.bsuir.models.SelfPropelledMachineTemplate;
import com.bsuir.repositories.SelfPropelledMachineTemplateRepository;
import com.bsuir.services.SelfPropelledMachineTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SelfPropelledMachineTemplateServiceImpl implements SelfPropelledMachineTemplateService {

    private final SelfPropelledMachineTemplateRepository templateRepository;

    public SelfPropelledMachineTemplateServiceImpl(SelfPropelledMachineTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public SelfPropelledMachineTemplate getById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Template with id " + id + " not found"));
    }

    @Override
    public List<SelfPropelledMachineTemplate> getAll() {
        return templateRepository.findAll();
    }

    @Override
    public SelfPropelledMachineTemplate save(SelfPropelledMachineTemplate template) {
        return templateRepository.save(template);
    }

    @Override
    public SelfPropelledMachineTemplate update(SelfPropelledMachineTemplate template) {
        return templateRepository.save(template);
    }

    @Override
    public void delete(SelfPropelledMachineTemplate template) {
        templateRepository.delete(template);
    }

    @Override
    public void deleteById(Long id) {
        templateRepository.deleteById(id);
    }
}
