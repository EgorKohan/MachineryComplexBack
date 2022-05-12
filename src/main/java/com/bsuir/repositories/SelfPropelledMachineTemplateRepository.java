package com.bsuir.repositories;

import com.bsuir.models.SelfPropelledMachineTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SelfPropelledMachineTemplateRepository extends JpaRepository<SelfPropelledMachineTemplate, Long> {

    Optional<SelfPropelledMachineTemplate> findByMachineName(String name);

    boolean existsByMachineName(String machineName);

}
