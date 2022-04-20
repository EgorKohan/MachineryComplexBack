package com.bsuir.controllers;


import com.bsuir.dtos.WorkPlanDto;
import com.bsuir.mappers.WorkPlanMapper;
import com.bsuir.models.WorkPlan;
import com.bsuir.services.WorkPlanService;
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
public class WorkPlanController {

	private final WorkPlanMapper workPlanMapper;
	private final WorkPlanService workPlanService;

	public WorkPlanController(WorkPlanMapper workPlanMapper,
							  WorkPlanService workPlanService) {
		this.workPlanMapper = workPlanMapper;
		this.workPlanService = workPlanService;
	}

	@GetMapping
	public List<WorkPlanDto> findAll() {
		List<WorkPlan> workPlans = workPlanService.findAll();
		return workPlanMapper.toDtos(workPlans);
	}

	@GetMapping("/{id}")
	public WorkPlanDto findById(@PathVariable("id") Long id) {
		WorkPlan workPlan = workPlanService.findById(id);
		return workPlanMapper.toDto(workPlan);
	}

	@PostMapping
	public WorkPlanDto create(@Valid @RequestBody WorkPlanDto dto) {
		WorkPlan workPlan = workPlanMapper.toWorkPlan(dto);
		WorkPlan savedWorkPlan = workPlanService.save(workPlan);
		return workPlanMapper.toDto(savedWorkPlan);
	}

	@PutMapping("/{id}")
	public WorkPlanDto update(
			@Valid @RequestBody WorkPlanDto dto,
			@PathVariable("id") Long id
	) {
		dto.setId(id);
		WorkPlan workPlan = workPlanMapper.toWorkPlan(dto);
		WorkPlan updatedWorkPlan = workPlanService.update(workPlan);
		return workPlanMapper.toDto(updatedWorkPlan);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Long id) {
		workPlanService.deleteById(id);
	}
}
