package com.bsuir.services;

import com.bsuir.models.SelfPropelledMachineTemplate;

import java.util.List;

public interface SelfPropelledMachineTemplateService {

    List<SelfPropelledMachineTemplate> findAll();

    SelfPropelledMachineTemplate findById(Long id);

    SelfPropelledMachineTemplate findByMachineName(String machineName);

    SelfPropelledMachineTemplate findByCodeId(String codeId);

    boolean isExistsById(Long id);

    boolean isExistsByMachineName(String machineName);

    boolean isExistsByCodeId(String codeId);

    SelfPropelledMachineTemplate save(SelfPropelledMachineTemplate template);

    SelfPropelledMachineTemplate update(SelfPropelledMachineTemplate template);

    void delete(SelfPropelledMachineTemplate template);

    void deleteById(Long id);

}
