package com.bsuir.controllers;

import com.bsuir.models.WorkPlan;
import com.bsuir.services.WorkPlanOptimizer;
import com.bsuir.services.WorkPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
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
    public Map<String, Object> calculateMissingEquipmentCost(
            @RequestBody List<Long> workPlanIds
    ) {
        List<WorkPlan> workPlans = workPlanService.findAllByIds(workPlanIds);
        log.debug("Work plans by ids {}: {}", workPlanIds, workPlans);
        return workPlanOptimizer.calculateMissingEquipmentCost(workPlans);
    }

}
