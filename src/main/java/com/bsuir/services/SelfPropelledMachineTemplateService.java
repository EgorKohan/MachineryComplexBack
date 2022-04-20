package com.bsuir.services;

import com.bsuir.models.SelfPropelledMachineTemplate;

import java.util.List;

public interface SelfPropelledMachineTemplateService {

    List<SelfPropelledMachineTemplate> findAll();

    SelfPropelledMachineTemplate findById(Long id);

    SelfPropelledMachineTemplate save(SelfPropelledMachineTemplate template);

    SelfPropelledMachineTemplate update(SelfPropelledMachineTemplate template);

    void delete(SelfPropelledMachineTemplate template);

    void deleteById(Long id);

}
