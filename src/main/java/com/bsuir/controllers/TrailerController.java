package com.bsuir.controllers;

import com.bsuir.dtos.TrailerDto;
import com.bsuir.mappers.TrailerMapper;
import com.bsuir.models.Trailer;
import com.bsuir.services.TrailerService;
import com.bsuir.utils.FileUploadUtil;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.bsuir.ApplicationConstants.TRAILERS_UPLOAD_DIR;

@RestController
@RequestMapping("/api/v1/trailers")
public class TrailerController {

    private final TrailerMapper trailerMapper;
    private final TrailerService trailerService;

    public TrailerController(TrailerMapper trailerMapper,
                             TrailerService trailerService) {
        this.trailerMapper = trailerMapper;
        this.trailerService = trailerService;
    }

    @GetMapping
    public List<TrailerDto> findAll() {
        List<Trailer> trailers = trailerService.findAll();
        return trailerMapper.toDtos(trailers);
    }

    @GetMapping(params = {"templateId"})
    public List<TrailerDto> findAllByTrailerTemplateId(@RequestParam(value = "templateId", required = false) Long templateId) {
        List<Trailer> trailers = trailerService.findAllByTrailerTemplateId(templateId);
        return trailerMapper.toDtos(trailers);
    }

    @GetMapping("/{id}")
    public TrailerDto findById(@PathVariable("id") Long id) {
        Trailer trailer = trailerService.findById(id);
        return trailerMapper.toDto(trailer);
    }

    @PostMapping(consumes = "multipart/form-data")
    public TrailerDto create(@Valid @ModelAttribute TrailerDto dto) {
        String pathToPhoto = FileUploadUtil.saveFile(TRAILERS_UPLOAD_DIR, dto.getPhoto());
        Trailer trailer = trailerMapper.toTrailer(dto);
        trailer.setPathToPhoto(pathToPhoto);
        Trailer savedTrailer = trailerService.save(trailer);
        return trailerMapper.toDto(savedTrailer);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public TrailerDto update(
            @Valid @ModelAttribute TrailerDto dto,
            @PathVariable("id") Long id
    ) {
        dto.setId(id);
        Trailer trailer = trailerMapper.toTrailer(dto);
        Trailer updatedTrailer = trailerService.update(trailer);
        return trailerMapper.toDto(updatedTrailer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        trailerService.deleteById(id);
    }
}
