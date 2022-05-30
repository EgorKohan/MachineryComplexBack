package com.bsuir.controllers;

import com.bsuir.dtos.SelfPropelledMachineTemplateDto;
import com.bsuir.mappers.SelfPropelledMachineTemplateMapper;
import com.bsuir.models.SelfPropelledMachineTemplate;
import com.bsuir.services.SelfPropelledMachineTemplateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public SelfPropelledMachineTemplateDto create(@Valid @ModelAttribute SelfPropelledMachineTemplateDto dto) {
        SelfPropelledMachineTemplate template = machineTemplateMapper.toTemplate(dto);
        SelfPropelledMachineTemplate savedTemplate = machineTemplateService.save(template);
        return machineTemplateMapper.toDto(savedTemplate);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public SelfPropelledMachineTemplateDto update(
            @Valid @ModelAttribute SelfPropelledMachineTemplateDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        SelfPropelledMachineTemplate template = machineTemplateMapper.toTemplate(dto);
        SelfPropelledMachineTemplate updatedTemplate = machineTemplateService.update(template);
        return machineTemplateMapper.toDto(updatedTemplate);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        machineTemplateService.deleteById(id);
    }

}
