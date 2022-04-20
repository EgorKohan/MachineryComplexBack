package com.bsuir.services.impl;

import com.bsuir.models.SelfPropelledMachine;
import com.bsuir.repositories.SelfPropelledMachineRepository;
import com.bsuir.services.SelfPropelledMachineService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SelfPropelledMachineServiceImpl implements SelfPropelledMachineService {

    private final SelfPropelledMachineRepository machineRepository;

    public SelfPropelledMachineServiceImpl(SelfPropelledMachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public List<SelfPropelledMachine> findAll() {
        return machineRepository.findAll();
    }

    @Override
    public SelfPropelledMachine findById(Long id) {
        return machineRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Machine with id " + id + " not found"));
    }

    @Override
    public SelfPropelledMachine save(SelfPropelledMachine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public SelfPropelledMachine update(SelfPropelledMachine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public void delete(SelfPropelledMachine machine) {
        machineRepository.delete(machine);
    }

    @Override
    public void deleteById(Long id) {
        machineRepository.deleteById(id);
    }
}
