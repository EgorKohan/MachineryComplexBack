package com.bsuir.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TrailerTemplateDto {

	private Long id;

	@NotBlank(message = "Trailer name cannot be blank")
	private String trailerName;

	@NotBlank(message = "Code ID cannot be blank")
	private String codeId;
}
