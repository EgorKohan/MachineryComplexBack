package com.bsuir.mappers;

import com.bsuir.dtos.WorkPlanDto;
import com.bsuir.models.AgriculturalOperation;
import com.bsuir.models.SelfPropelledMachineTemplate;
import com.bsuir.models.TrailerTemplate;
import com.bsuir.models.WorkPlan;
import com.bsuir.services.AgriculturalOperationService;
import com.bsuir.services.SelfPropelledMachineTemplateService;
import com.bsuir.services.TrailerTemplateService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class WorkPlanMapper {

    @Autowired
    protected SelfPropelledMachineTemplateService machineTemplateService;

    @Autowired
    protected TrailerTemplateService trailerTemplateService;

    @Autowired
    protected AgriculturalOperationService agriculturalOperationService;

    @BeforeMapping
    protected void setUp(@MappingTarget WorkPlan workPlan, WorkPlanDto dto) {
        SelfPropelledMachineTemplate machineTemplate = machineTemplateService.findById(dto.getMachineTemplateId());
        workPlan.setMachineTemplate(machineTemplate);
        TrailerTemplate trailerTemplate = trailerTemplateService.findById(dto.getTrailerTemplateId());
        workPlan.setTrailerTemplate(trailerTemplate);
        AgriculturalOperation operation = agriculturalOperationService.findById(dto.getOperationId());
        workPlan.setOperation(operation);
    }

    @AfterMapping
    protected void enrichIds(@MappingTarget WorkPlan workPlan) {
		workPlan.getPeriods().forEach(period -> period.setWorkPlan(workPlan));
    }

    @Mapping(target = "machineTemplate", ignore = true)
    @Mapping(target = "trailerTemplate", ignore = true)
    @Mapping(target = "operation", ignore = true)
    public abstract WorkPlan toWorkPlan(WorkPlanDto dto);

    @Mapping(target = "machineTemplateId", source = "workPlan.machineTemplate.id")
    @Mapping(target = "trailerTemplateId", source = "workPlan.trailerTemplate.id")
    @Mapping(target = "operationId", source = "workPlan.operation.id")
    public abstract WorkPlanDto toDto(WorkPlan workPlan);

    public abstract List<WorkPlanDto> toDtos(List<WorkPlan> workPlans);

    public abstract List<WorkPlan> toWorkPlans(List<WorkPlanDto> dtos);
}
