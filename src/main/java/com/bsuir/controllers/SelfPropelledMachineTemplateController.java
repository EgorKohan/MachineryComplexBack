package com.bsuir.controllers;

import com.bsuir.dtos.SelfPropelledMachineTemplateDto;
import com.bsuir.mappers.SelfPropelledMachineTemplateMapper;
import com.bsuir.models.SelfPropelledMachineTemplate;
import com.bsuir.services.SelfPropelledMachineTemplateService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/machine-template")
public class SelfPropelledMachineTemplateController {

    private final SelfPropelledMachineTemplateService templateService;
    private final SelfPropelledMachineTemplateMapper templateMapper;

    public SelfPropelledMachineTemplateController(
            SelfPropelledMachineTemplateService templateService,
            SelfPropelledMachineTemplateMapper templateMapper
    ) {
        this.templateService = templateService;
        this.templateMapper = templateMapper;
    }

    @GetMapping
    public List<SelfPropelledMachineTemplateDto> getAll() {
        List<SelfPropelledMachineTemplate> all = templateService.getAll();
        return templateMapper.toDtos(all);
    }

    @PostMapping
    public SelfPropelledMachineTemplateDto create(@Valid @RequestBody SelfPropelledMachineTemplateDto dto) {
        SelfPropelledMachineTemplate template = templateMapper.toTemplate(dto);
        SelfPropelledMachineTemplate savedTemplate = templateService.save(template);
        return templateMapper.toDto(savedTemplate);
    }

    @PutMapping("/{id}")
    public SelfPropelledMachineTemplateDto update(
            @Valid @RequestBody SelfPropelledMachineTemplateDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        SelfPropelledMachineTemplate template = templateMapper.toTemplate(dto);
        SelfPropelledMachineTemplate updatedTemplate = templateService.update(template);
        return templateMapper.toDto(updatedTemplate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        templateService.deleteById(id);
    }

}
