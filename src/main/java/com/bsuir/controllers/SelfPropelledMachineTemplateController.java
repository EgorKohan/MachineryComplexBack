package com.bsuir.controllers;

import com.bsuir.dtos.SelfPropelledMachineTemplateDto;
import com.bsuir.dtos.view.View;
import com.bsuir.mappers.SelfPropelledMachineTemplateMapper;
import com.bsuir.models.SelfPropelledMachineTemplate;
import com.bsuir.services.SelfPropelledMachineTemplateService;
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
@RequestMapping("/api/v1/machine-templates")
public class SelfPropelledMachineTemplateController {

    private final SelfPropelledMachineTemplateService machineTemplateService;
    private final SelfPropelledMachineTemplateMapper machineTemplateMapper;

    public SelfPropelledMachineTemplateController(
            SelfPropelledMachineTemplateService machineTemplateService,
            SelfPropelledMachineTemplateMapper machineTemplateMapper) {
        this.machineTemplateService = machineTemplateService;
        this.machineTemplateMapper = machineTemplateMapper;
    }

    @GetMapping
    public List<SelfPropelledMachineTemplateDto> findAll() {
        List<SelfPropelledMachineTemplate> templates = machineTemplateService.findAll();
        return machineTemplateMapper.toDtos(templates);
    }

    @GetMapping("/{id}")
    public SelfPropelledMachineTemplateDto findById(@PathVariable("id") Long id) {
        SelfPropelledMachineTemplate template = machineTemplateService.findById(id);
        return machineTemplateMapper.toDto(template);
    }

    @PostMapping
    public SelfPropelledMachineTemplateDto create(@JsonView(View.Save.class) @Valid @RequestBody SelfPropelledMachineTemplateDto dto) {
        SelfPropelledMachineTemplate template = machineTemplateMapper.toTemplate(dto);
        SelfPropelledMachineTemplate savedTemplate = machineTemplateService.save(template);
        return machineTemplateMapper.toDto(savedTemplate);
    }

    @PutMapping("/{id}")
    public SelfPropelledMachineTemplateDto update(
            @JsonView(View.Save.class) @Valid @RequestBody SelfPropelledMachineTemplateDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        SelfPropelledMachineTemplate template = machineTemplateMapper.toTemplate(dto);
        SelfPropelledMachineTemplate updatedTemplate = machineTemplateService.update(template);
        return machineTemplateMapper.toDto(updatedTemplate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        machineTemplateService.deleteById(id);
    }

}
