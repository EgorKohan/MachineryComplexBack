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

    @GetMapping(params = {"templateId"})
    public List<SelfPropelledMachineDto> findAllByMachineTemplateId(@RequestParam(value = "templateId", required = false) Long templateId) {
        List<SelfPropelledMachine> machines = machineService.findAllByMachineTemplateId(templateId);
        return machineMapper.toDtos(machines);
    }

    @GetMapping("/{id}")
    public SelfPropelledMachineDto findById(@PathVariable("id") Long id) {
        SelfPropelledMachine machine = machineService.findById(id);
        return machineMapper.toDto(machine);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public SelfPropelledMachineDto create(@Valid @ModelAttribute SelfPropelledMachineDto dto) {
        SelfPropelledMachine machine = machineMapper.toMachine(dto);
        SelfPropelledMachine savedMachine = machineService.save(machine);
        return machineMapper.toDto(savedMachine);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public SelfPropelledMachineDto update(
            @Valid @ModelAttribute SelfPropelledMachineDto dto,
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
