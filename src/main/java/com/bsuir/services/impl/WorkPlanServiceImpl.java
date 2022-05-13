package com.bsuir.services.impl;

import com.bsuir.models.WorkPlan;
import com.bsuir.repositories.WorkPlanRepository;
import com.bsuir.services.WorkPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WorkPlanServiceImpl implements WorkPlanService {

    private final WorkPlanRepository workPlanRepository;

    public WorkPlanServiceImpl(WorkPlanRepository workPlanRepository) {
        this.workPlanRepository = workPlanRepository;
    }

    @Override
    public List<WorkPlan> findAll() {
        return workPlanRepository.findAll();
    }

    @Override
    public WorkPlan findById(Long id) {
        return workPlanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Work plan with id " + id + " not found"));
    }

    @Override
    public List<WorkPlan> findAllByIds(List<Long> ids) {
        return workPlanRepository.findAllByIdIn(ids);
    }

    @Override
    public WorkPlan save(WorkPlan workPlan) {
        return workPlanRepository.save(workPlan);
    }

    @Override
    public WorkPlan update(WorkPlan workPlan) {
        return workPlanRepository.save(workPlan);
    }

    @Override
    public void delete(WorkPlan workPlan) {
        workPlanRepository.delete(workPlan);
    }

    @Override
    public void deleteById(Long id) {
        workPlanRepository.deleteById(id);
    }
}
