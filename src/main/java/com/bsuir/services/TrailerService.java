package com.bsuir.services;

import com.bsuir.models.Trailer;

import java.util.List;

public interface TrailerService {

	List<Trailer> findAll();

	Trailer findById(Long id);

	Trailer save(Trailer trailer);

	Trailer update(Trailer trailer);

	void delete(Trailer trailer);

	void deleteById(Long id);
}
