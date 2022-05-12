package com.bsuir.repositories;

import com.bsuir.models.TrailerTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrailerTemplateRepository extends JpaRepository<TrailerTemplate, Long> {

    Optional<TrailerTemplate> findByTrailerName(String trailerName);

    boolean existsByTrailerName(String trailerName);

}
