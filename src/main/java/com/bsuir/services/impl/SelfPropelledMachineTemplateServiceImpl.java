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

    private final SelfPropelledMachineTemplateRepository machineTemplateRepository;

    public SelfPropelledMachineTemplateServiceImpl(SelfPropelledMachineTemplateRepository machineTemplateRepository) {
        this.machineTemplateRepository = machineTemplateRepository;
    }

    @Override
    public SelfPropelledMachineTemplate findById(Long id) {
        return machineTemplateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Machine template with id " + id + " not found"));
    }

    @Override
    public List<SelfPropelledMachineTemplate> findAll() {
        return machineTemplateRepository.findAll();
    }

    @Override
    public SelfPropelledMachineTemplate save(SelfPropelledMachineTemplate template) {
        return machineTemplateRepository.save(template);
    }

    @Override
    public SelfPropelledMachineTemplate update(SelfPropelledMachineTemplate template) {
        return machineTemplateRepository.save(template);
    }

    @Override
    public void delete(SelfPropelledMachineTemplate template) {
        machineTemplateRepository.delete(template);
    }

    @Override
    public void deleteById(Long id) {
        machineTemplateRepository.deleteById(id);
    }
}
