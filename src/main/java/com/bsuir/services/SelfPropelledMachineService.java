package com.bsuir.services;

import com.bsuir.models.SelfPropelledMachine;

import java.util.List;

public interface SelfPropelledMachineService {

    List<SelfPropelledMachine> findAll();

    List<SelfPropelledMachine> findAllByMachineTemplateId(Long templateId);

    SelfPropelledMachine findById(Long id);

    SelfPropelledMachine save(SelfPropelledMachine template);

    SelfPropelledMachine update(SelfPropelledMachine template);

    void delete(SelfPropelledMachine template);

    void deleteById(Long id);


}
