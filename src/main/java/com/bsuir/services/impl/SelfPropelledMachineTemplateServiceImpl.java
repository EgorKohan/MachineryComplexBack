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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Machine template with id " + id + " not found"));
    }

    @Override
    public SelfPropelledMachineTemplate findByMachineName(String machineName) {
        return machineTemplateRepository.findByMachineName(machineName).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Machine template with name " + machineName + " not found")
        );
    }


    @Override
    public boolean isExistsById(Long id) {
        return machineTemplateRepository.existsById(id);
    }

    @Override
    public boolean isExistsByMachineName(String machineName) {
        return machineTemplateRepository.existsByMachineName(machineName);
    }

    @Override
    public List<SelfPropelledMachineTemplate> findAll() {
        return machineTemplateRepository.findAll();
    }

    @Override
    public SelfPropelledMachineTemplate save(SelfPropelledMachineTemplate template) {
        template.setId(null);
        checkMachineNameUniqueness(template.getMachineName());
        return machineTemplateRepository.save(template);
    }

    @Override
    public SelfPropelledMachineTemplate update(SelfPropelledMachineTemplate template) {
        checkMachineNameUniqueness(template.getMachineName());
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

    private void checkMachineNameUniqueness(String machineName) {
        if(isExistsByMachineName(machineName)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Machine template with machine name " + machineName + " is exists");
    }

}
