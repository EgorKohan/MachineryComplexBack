package com.bsuir.repositories;

import com.bsuir.models.Trailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {

    List<Trailer> findAllByTrailerTemplateId(Long trailerTemplateId);

    Long countTrailersByTrailerTemplateId(Long trailerTemplateId);

}
