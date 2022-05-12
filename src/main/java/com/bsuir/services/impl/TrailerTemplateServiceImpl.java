package com.bsuir.services.impl;

import com.bsuir.models.TrailerTemplate;
import com.bsuir.repositories.TrailerTemplateRepository;
import com.bsuir.services.TrailerTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrailerTemplateServiceImpl implements TrailerTemplateService {

    private final TrailerTemplateRepository trailerTemplateRepository;

    public TrailerTemplateServiceImpl(TrailerTemplateRepository trailerTemplateRepository) {
        this.trailerTemplateRepository = trailerTemplateRepository;
    }

    @Override
    public List<TrailerTemplate> findAll() {
        return trailerTemplateRepository.findAll();
    }

    @Override
    public TrailerTemplate findById(Long id) {
        return trailerTemplateRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trailer template with id " + id + " not found"));
    }

    @Override
    public TrailerTemplate save(TrailerTemplate template) {
        template.setId(null);
        checkTrailerNameUniqueness(template.getTrailerName());
        checkCodeIdUniqueness(template.getCodeId());
        return trailerTemplateRepository.save(template);
    }

    @Override
    public TrailerTemplate update(TrailerTemplate template) {
        checkTrailerNameUniqueness(template.getTrailerName());
        checkCodeIdUniqueness(template.getCodeId());
        return trailerTemplateRepository.save(template);
    }

    @Override
    public void delete(TrailerTemplate template) {
        trailerTemplateRepository.delete(template);
    }

    @Override
    public void deleteById(Long id) {
        trailerTemplateRepository.deleteById(id);
    }

    @Override
    public boolean isExistsById(Long id) {
        return trailerTemplateRepository.existsById(id);
    }

    @Override
    public boolean isExistsByTrailerName(String trailerName) {
        return trailerTemplateRepository.existsByTrailerName(trailerName);
    }

    @Override
    public boolean isExistsByCodeId(String codeId) {
        return trailerTemplateRepository.existsByCodeId(codeId);
    }

    private void checkTrailerNameUniqueness(String trailerName) {
        if(isExistsByTrailerName(trailerName)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trailer template with trailer name " + trailerName + " is exists");
    }

    private void checkCodeIdUniqueness(String codeId) {
        if(isExistsByCodeId(codeId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trailer template with code id " + codeId + " is exists");
    }

}
