package com.bsuir.services.impl;

import com.bsuir.exceptions.CustomValidationException;
import com.bsuir.models.Period;
import com.bsuir.models.WorkPlan;
import com.bsuir.repositories.WorkPlanRepository;
import com.bsuir.services.WorkPlanService;
import com.bsuir.validation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Work plan with id " + id + " not found"));
    }

    @Override
    public List<WorkPlan> findAllByIds(List<Long> ids) {
        return workPlanRepository.findAllByIdIn(ids);
    }

    @Override
    public WorkPlan save(WorkPlan workPlan) {
        checkPeriods(workPlan);
        checkPeriodsForOperation(workPlan);
        return workPlanRepository.save(workPlan);
    }

    @Override
    public WorkPlan update(WorkPlan workPlan) {
        if(workPlan.getId() == null) throw new IllegalArgumentException("Work plan ID cannot be null!");
        checkPeriods(workPlan);
        checkPeriodsForOperation(workPlan);
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

    private void checkPeriods(WorkPlan workPlan) {
        Set<Period> periods = workPlan.getPeriods();
        List<Violation> violations = new LinkedList<>();
        int number = 0;
        periods.forEach(period -> {
            Date endDate = period.getEndDate();
            Date startDate = period.getStartDate();
            if (startDate.after(endDate) || startDate.equals(endDate)) {
                violations.add(new Violation("period." + number, "Period's end date " + endDate + " must be greater than start date " + startDate));
            }
        });
        if (!violations.isEmpty()) throw new CustomValidationException(violations);
    }

    private void checkPeriodsForOperation(WorkPlan workPlan) {
        Date operationStartDate = workPlan.getOperation().getStartDate();
        Date operationEndDate = workPlan.getOperation().getEndDate();
        Set<Period> periods = workPlan.getPeriods();
        List<Violation> violations = new LinkedList<>();
        int number = 0;
        for (Period period : periods) {
            Date periodEndDate = period.getEndDate();
            Date periodStartDate = period.getStartDate();
            if (periodStartDate.before(operationStartDate) || periodEndDate.after(operationEndDate)) {
                violations.add(new Violation("period." + number, "period must be in operation's dates"));
            }
            number++;
        }
        if (!violations.isEmpty()) throw new CustomValidationException(violations);
    }

}
