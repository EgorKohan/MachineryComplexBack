package com.bsuir.repositories;

import com.bsuir.models.WorkPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkPlanRepository extends JpaRepository<WorkPlan, Long> {

    List<WorkPlan> findAllByIdIn(List<Long> ids);

}
