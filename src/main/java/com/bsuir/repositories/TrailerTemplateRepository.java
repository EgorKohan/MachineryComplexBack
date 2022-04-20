package com.bsuir.repositories;

import com.bsuir.models.TrailerTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailerTemplateRepository extends JpaRepository<TrailerTemplate, Long> {
}
