package com.bsuir.services;

import com.bsuir.models.TrailerTemplate;

import java.util.List;

public interface TrailerTemplateService {

	List<TrailerTemplate> findAll();

	TrailerTemplate findById(Long id);

	TrailerTemplate save(TrailerTemplate template);

	TrailerTemplate update(TrailerTemplate template);

	void delete(TrailerTemplate template);

	void deleteById(Long id);
}
