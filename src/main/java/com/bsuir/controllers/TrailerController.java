package com.bsuir.controllers;

import com.bsuir.dtos.TrailerDto;
import com.bsuir.mappers.TrailerMapper;
import com.bsuir.models.Trailer;
import com.bsuir.services.TrailerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trailers")
public class TrailerController {

	private final TrailerMapper trailerMapper;
	private final TrailerService trailerService;

	public TrailerController(TrailerMapper trailerMapper,
							 TrailerService trailerService) {
		this.trailerMapper = trailerMapper;
		this.trailerService = trailerService;
	}

	@GetMapping
	public List<TrailerDto> findAll() {
		List<Trailer> trailers = trailerService.findAll();
		return trailerMapper.toDtos(trailers);
	}

	@GetMapping("/{id}")
	public TrailerDto findById(@PathVariable("id") Long id) {
		Trailer trailer = trailerService.findById(id);
		return trailerMapper.toDto(trailer);
	}

	@PostMapping
	public TrailerDto create(@Valid @RequestBody TrailerDto dto) {
		Trailer trailer = trailerMapper.toTrailer(dto);
		Trailer savedTrailer = trailerService.save(trailer);
		return trailerMapper.toDto(savedTrailer);
	}

	@PutMapping("/{id}")
	public TrailerDto update(
			@Valid @RequestBody TrailerDto dto,
			@PathVariable("id") Long id
	) {
		dto.setId(id);
		Trailer trailer = trailerMapper.toTrailer(dto);
		Trailer updatedTrailer = trailerService.update(trailer);
		return trailerMapper.toDto(updatedTrailer);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		trailerService.deleteById(id);
	}
}
