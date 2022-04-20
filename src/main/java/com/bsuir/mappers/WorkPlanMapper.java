package com.bsuir.mappers;

import com.bsuir.dtos.WorkPlanDto;
import com.bsuir.models.AgriculturalOperation;
import com.bsuir.models.SelfPropelledMachine;
import com.bsuir.models.Trailer;
import com.bsuir.models.WorkPlan;
import com.bsuir.services.AgriculturalOperationService;
import com.bsuir.services.SelfPropelledMachineService;
import com.bsuir.services.TrailerService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class WorkPlanMapper {

	@Autowired
	protected SelfPropelledMachineService machineService;

	@Autowired
	protected TrailerService trailerService;

	@Autowired
	protected AgriculturalOperationService agriculturalOperationService;

	@BeforeMapping
	protected void setUp(@MappingTarget WorkPlan workPlan, WorkPlanDto dto) {
		SelfPropelledMachine machine = machineService.findById(dto.getMachineId());
		workPlan.setMachine(machine);
		Trailer trailer = trailerService.findById(dto.getTrailerId());
		workPlan.setTrailer(trailer);
		AgriculturalOperation operation = agriculturalOperationService.findById(dto.getOperationId());
		workPlan.setOperation(operation);
	}

	@Mapping(target = "machine", ignore = true)
	@Mapping(target = "trailer", ignore = true)
	@Mapping(target = "operation", ignore = true)
	public abstract WorkPlan toWorkPlan(WorkPlanDto dto);

	@Mapping(target = "machineId", source = "workPlan.machine.id")
	@Mapping(target = "trailerId", source = "workPlan.trailer.id")
	@Mapping(target = "operationId", source = "workPlan.operation.id")
	public abstract WorkPlanDto toDto(WorkPlan workPlan);

	public abstract List<WorkPlanDto> toDtos(List<WorkPlan> workPlans);

	public abstract List<WorkPlan> toWorkPlans(List<WorkPlanDto> dtos);
}
