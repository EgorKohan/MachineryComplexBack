package com.bsuir.dtos;

import com.bsuir.dtos.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class WorkPlanDto {

	private Long id;

	@JsonView(View.Save.class)
	@NotNull(message = "Machine ID cannot be null")
	private Long machineId;

	@JsonView(View.Save.class)
	@NotNull(message = "Trailer ID cannot be null")
	private Long trailerId;

	@JsonView(View.Save.class)
	@NotNull(message = "Agricultural operation ID cannot be null")
	private Long operationId;

	@JsonView(View.Save.class)
	@NotNull(message = "Work per shift cannot be null")
	@Min(value = 0, message = "Work per shift cannot be less than 0")
	private Float workPerShift;
}
