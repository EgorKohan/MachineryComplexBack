package com.bsuir.services;

import com.bsuir.models.WorkPlan;

import java.util.Collection;
import java.util.Map;

public interface WorkPlanOptimizer {

    Map<String, Object> calculateMissingEquipmentCost(Collection<WorkPlan> workPlans);

}
