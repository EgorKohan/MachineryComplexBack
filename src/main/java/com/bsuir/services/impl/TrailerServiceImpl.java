package com.bsuir.services.impl;

import com.bsuir.models.Trailer;
import com.bsuir.repositories.TrailerRepository;
import com.bsuir.services.TrailerService;
import com.bsuir.services.TrailerTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrailerServiceImpl implements TrailerService {

    private final TrailerRepository trailerRepository;
    private final TrailerTemplateService templateService;

    public TrailerServiceImpl(
            TrailerRepository trailerRepository,
            TrailerTemplateService templateService
    ) {
        this.trailerRepository = trailerRepository;
        this.templateService = templateService;
    }

    @Override
    public List<Trailer> findAll() {
        return trailerRepository.findAll();
    }

    @Override
    public List<Trailer> findAllByTrailerTemplateId(Long templateId) {
        checkThatTrailerTemplateIdExists(templateId);
        return trailerRepository.findAllByTrailerTemplateId(templateId);
    }

    @Override
    public Long countByTrailerTemplateId(Long templateId) {
        checkThatTrailerTemplateIdExists(templateId);
        return trailerRepository.countTrailersByTrailerTemplateId(templateId);
    }

    @Override
    public Trailer findById(Long id) {
        return trailerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trailer with id " + id + " not found"));
    }

    @Override
    public Trailer save(Trailer trailer) {
        return trailerRepository.save(trailer);
    }

    @Override
    public Trailer update(Trailer trailer) {
        return trailerRepository.save(trailer);
    }

    @Override
    public void delete(Trailer trailer) {
        trailerRepository.delete(trailer);
    }

    @Override
    public void deleteById(Long id) {
        trailerRepository.deleteById(id);
    }

    private void checkThatTrailerTemplateIdExists(Long templateId) {
        if (templateService.isExistsById(templateId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trailer template with id " + templateId + " doesn't exist");
    }

}
