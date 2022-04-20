package com.bsuir.services.impl;

import com.bsuir.models.Trailer;
import com.bsuir.repositories.TrailerRepository;
import com.bsuir.services.TrailerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TrailerServiceImpl implements TrailerService {

	private final TrailerRepository trailerRepository;

	public TrailerServiceImpl(TrailerRepository trailerRepository) {
		this.trailerRepository = trailerRepository;
	}

	@Override
	public List<Trailer> findAll() {
		return trailerRepository.findAll();
	}

	@Override
	public Trailer findById(Long id) {
		return trailerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trailer with id " + id + " not found"));
	}

	@Override
	public Trailer save(Trailer trailer) {
		return trailerRepository.save(trailer);
	}

	@Override
	public Trailer update(Trailer trailer) {
		return trailerRepository.save(trailer);
	}

	@Override
	public void delete(Trailer trailer) {
		trailerRepository.delete(trailer);
	}

	@Override
	public void deleteById(Long id) {
		trailerRepository.deleteById(id);
	}
}
