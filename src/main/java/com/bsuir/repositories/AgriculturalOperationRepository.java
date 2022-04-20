package com.bsuir.repositories;

import com.bsuir.models.AgriculturalOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgriculturalOperationRepository extends JpaRepository<AgriculturalOperation, Long> {
}
