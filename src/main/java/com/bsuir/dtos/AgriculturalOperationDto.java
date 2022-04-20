package com.bsuir.dtos;

import com.bsuir.dtos.view.View;
import com.bsuir.models.EUnit;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AgriculturalOperationDto {

	private Long id;

	@JsonView(View.Save.class)
	@NotBlank(message = "Code ID cannot be blank")
	private String codeId;

	@JsonView(View.Save.class)
	@NotBlank(message = "Name cannot be blank")
	private String name;

	@JsonView(View.Save.class)
	@NotNull(message = "Work volume cannot be null")
	@Min(value = 0, message = "Work volume cannot be less than 0")
	private Float workVolume;

	@JsonView(View.Save.class)
	@NotNull(message = "Unit cannot be null")
	private EUnit unit;

	@JsonView(View.Save.class)
	@NotNull(message = "Start date cannot be null")
	private LocalDateTime startDate;

	@JsonView(View.Save.class)
	@NotNull(message = "End date cannot be null")
	private LocalDateTime endDate;
}
