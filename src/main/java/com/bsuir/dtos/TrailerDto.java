package com.bsuir.dtos;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TrailerDto {

	private Long id;

	@NotBlank(message = "Inventory number cannot be blank")
	private String inventoryNumber;

	@NotNull(message = "Trailer template ID cannot be null")
	private Long trailerTemplateId;

	@NotNull(message = "Actual service life cannot be null")
	@Min(value = 0, message = "Actual service life cannot be less than 0")
	private Integer actualServiceLifeInMonth;

	@NotNull(message = "Normative service life cannot be null")
	@Min(value = 0, message = "Normative service life cannot be less than 0")
	private Integer normativeServiceLifeInMonth;

	@NotNull(message = "Initial cost cannot be null")
	@Min(value = 0, message = "Initial cost cannot be less than 0")
	private BigDecimal initialCost;

	@NotNull(message = "Residual value cannot be null")
	@Min(value = 0, message = "Residual value cannot be less than 0")
	private BigDecimal residualValue;

	@NotNull(message = "Technical readiness factor cannot be null")
	@Min(value = 0, message = "Technical readiness factor cannot be less than 0")
	@Max(value = 1, message = "Technical readiness factor cannot be more than 1")
	private Float trk;
}
