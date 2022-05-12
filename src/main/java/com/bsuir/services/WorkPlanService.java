package com.bsuir.services;

import com.bsuir.models.WorkPlan;

import java.util.List;

public interface WorkPlanService {

	List<WorkPlan> findAll();

	WorkPlan findById(Long id);

	List<WorkPlan> findAllByIds(List<Long> ids);

	WorkPlan save(WorkPlan workPlan);

	WorkPlan update(WorkPlan workPlan);

	void delete(WorkPlan workPlan);

	void deleteById(Long id);
}
