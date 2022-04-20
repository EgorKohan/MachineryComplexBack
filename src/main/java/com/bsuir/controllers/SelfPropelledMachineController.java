package com.bsuir.controllers;

import com.bsuir.dtos.SelfPropelledMachineDto;
import com.bsuir.dtos.view.View;
import com.bsuir.mappers.SelfPropelledMachineMapper;
import com.bsuir.models.SelfPropelledMachine;
import com.bsuir.services.SelfPropelledMachineService;
import com.fasterxml.jackson.annotation.JsonView;
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
@RequestMapping("/api/v1/machines")
public class SelfPropelledMachineController {

    private final SelfPropelledMachineMapper machineMapper;
    private final SelfPropelledMachineService machineService;

    public SelfPropelledMachineController(
            SelfPropelledMachineMapper machineMapper,
            SelfPropelledMachineService machineService) {
        this.machineMapper = machineMapper;
        this.machineService = machineService;
    }

    @GetMapping
    public List<SelfPropelledMachineDto> findAll() {
        List<SelfPropelledMachine> machines = machineService.findAll();
        return machineMapper.toDtos(machines);
    }

    @GetMapping("/{id}")
    public SelfPropelledMachineDto findById(@PathVariable("id") Long id) {
        SelfPropelledMachine machine = machineService.findById(id);
        return machineMapper.toDto(machine);
    }

    @PostMapping
    public SelfPropelledMachineDto create(@JsonView(View.Save.class) @Valid @RequestBody SelfPropelledMachineDto dto) {
        SelfPropelledMachine machine = machineMapper.toMachine(dto);
        SelfPropelledMachine savedMachine = machineService.save(machine);
        return machineMapper.toDto(savedMachine);
    }

    @PutMapping("/{id}")
    public SelfPropelledMachineDto update(
            @JsonView(View.Save.class) @Valid @RequestBody SelfPropelledMachineDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        SelfPropelledMachine machine = machineMapper.toMachine(dto);
        SelfPropelledMachine updatedMachine = machineService.update(machine);
        return machineMapper.toDto(updatedMachine);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        machineService.deleteById(id);
    }

}
