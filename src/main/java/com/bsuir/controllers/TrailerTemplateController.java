package com.bsuir.controllers;

import com.bsuir.dtos.TrailerTemplateDto;
import com.bsuir.mappers.TrailerTemplateMapper;
import com.bsuir.models.TrailerTemplate;
import com.bsuir.services.TrailerTemplateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trailer-templates")
public class TrailerTemplateController {

    private final TrailerTemplateMapper trailerTemplateMapper;
    private final TrailerTemplateService trailerTemplateService;

    public TrailerTemplateController(TrailerTemplateMapper trailerTemplateMapper,
                                     TrailerTemplateService trailerTemplateService) {
        this.trailerTemplateMapper = trailerTemplateMapper;
        this.trailerTemplateService = trailerTemplateService;
    }

    @GetMapping
    public List<TrailerTemplateDto> findAll() {
        List<TrailerTemplate> trailerTemplates = trailerTemplateService.findAll();
        return trailerTemplateMapper.toDtos(trailerTemplates);
    }

    @GetMapping("/{id}")
    public TrailerTemplateDto findById(@PathVariable("id") Long id) {
        TrailerTemplate trailerTemplate = trailerTemplateService.findById(id);
        return trailerTemplateMapper.toDto(trailerTemplate);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public TrailerTemplateDto create(@Valid @ModelAttribute TrailerTemplateDto dto) {
        TrailerTemplate trailerTemplate = trailerTemplateMapper.toTemplate(dto);
        TrailerTemplate savedTrailerTemplate = trailerTemplateService.save(trailerTemplate);
        return trailerTemplateMapper.toDto(savedTrailerTemplate);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public TrailerTemplateDto update(
            @Valid @ModelAttribute TrailerTemplateDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        TrailerTemplate trailerTemplate = trailerTemplateMapper.toTemplate(dto);
        TrailerTemplate updatedTrailerTemplate = trailerTemplateService.update(trailerTemplate);
        return trailerTemplateMapper.toDto(updatedTrailerTemplate);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        trailerTemplateService.deleteById(id);
    }
}
