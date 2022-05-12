package com.bsuir.controllers;

import com.bsuir.models.WorkPlan;
import com.bsuir.services.WorkPlanOptimizer;
import com.bsuir.services.WorkPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/optimize")
public class WorkPlanOptimizerController {

    private final WorkPlanService workPlanService;
    private final WorkPlanOptimizer workPlanOptimizer;

    @Autowired
    public WorkPlanOptimizerController(
            WorkPlanService workPlanService,
            WorkPlanOptimizer workPlanOptimizer
    ) {
        this.workPlanService = workPlanService;
        this.workPlanOptimizer = workPlanOptimizer;
    }

    @PostMapping
    public Double calculateMissingEquipmentCost(
            @RequestBody List<Long> workPlanIds
    ) {
        List<WorkPlan> workPlans = workPlanService.findAllByIds(workPlanIds);
        log.debug("Work plans by ids {}: {}", workPlanIds, workPlans);
        return workPlanOptimizer.calculateMissingEquipmentCost(workPlans);
    }

}
