package com.bsuir.controllers;

import com.bsuir.dtos.SelfPropelledMachineDto;
import com.bsuir.mappers.SelfPropelledMachineMapper;
import com.bsuir.models.SelfPropelledMachine;
import com.bsuir.services.SelfPropelledMachineService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/machines")
public class SelfPropelledMachineController {

    private final SelfPropelledMachineMapper mapper;
    private final SelfPropelledMachineService machineService;

    public SelfPropelledMachineController(
            SelfPropelledMachineMapper mapper,
            SelfPropelledMachineService machineService
    ) {
        this.mapper = mapper;
        this.machineService = machineService;
    }

    @GetMapping
    public List<SelfPropelledMachineDto> getAll() {
        return mapper.toDtos(machineService.getAll());
    }

    @PostMapping
    public SelfPropelledMachineDto create(@Valid @RequestBody SelfPropelledMachineDto dto) {
        SelfPropelledMachine machine = mapper.toMachine(dto);
        return mapper.toDto(machineService.create(machine));
    }

    @PutMapping("/{id}")
    public SelfPropelledMachineDto update(
            @Valid @RequestBody SelfPropelledMachineDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        SelfPropelledMachine machine = mapper.toMachine(dto);
        return mapper.toDto(machineService.update(machine));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        machineService.deleteById(id);
    }

}
