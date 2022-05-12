package com.bsuir.services;

import com.bsuir.models.WorkPlan;

import java.util.Collection;

public interface WorkPlanOptimizer {

    Double calculateMissingEquipmentCost(Collection<WorkPlan> workPlans);

}
