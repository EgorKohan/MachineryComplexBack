package com.bsuir.repositories;

import com.bsuir.models.Trailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {
}
