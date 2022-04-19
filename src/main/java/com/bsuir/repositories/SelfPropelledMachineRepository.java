package com.bsuir.repositories;

import com.bsuir.models.SelfPropelledMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfPropelledMachineRepository extends JpaRepository<SelfPropelledMachine, Long> {
}
