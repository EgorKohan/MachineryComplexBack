package com.bsuir.dtos;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class WorkPlanDto {

	private Long id;

	@NotNull(message = "Machine ID cannot be null")
	private Long machineId;

	@NotNull(message = "Trailer ID cannot be null")
	private Long trailerId;

	@NotNull(message = "Agricultural operation ID cannot be null")
	private Long operationId;

	@NotNull(message = "Work per shift cannot be null")
	@Min(value = 0, message = "Work per shift cannot be less than 0")
	private Float workPerShift;
}
