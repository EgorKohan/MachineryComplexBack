package com.bsuir.repositories;

import com.bsuir.models.SelfPropelledMachineTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfPropelledMachineTemplateRepository extends JpaRepository<SelfPropelledMachineTemplate, Long> {
}
