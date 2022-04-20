package com.bsuir.services;

import com.bsuir.models.SelfPropelledMachineTemplate;

import java.util.List;

public interface SelfPropelledMachineTemplateService {

    SelfPropelledMachineTemplate getById(Long id);

    List<SelfPropelledMachineTemplate> getAll();

    SelfPropelledMachineTemplate save(SelfPropelledMachineTemplate template);

    SelfPropelledMachineTemplate update(SelfPropelledMachineTemplate template);

    void delete(SelfPropelledMachineTemplate template);

    void deleteById(Long id);

}
