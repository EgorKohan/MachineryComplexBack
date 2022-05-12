package com.bsuir.repositories;

import com.bsuir.models.SelfPropelledMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfPropelledMachineRepository extends JpaRepository<SelfPropelledMachine, Long> {


    List<SelfPropelledMachine> findAllByMachineTemplateId(Long machineTemplateId);

}
