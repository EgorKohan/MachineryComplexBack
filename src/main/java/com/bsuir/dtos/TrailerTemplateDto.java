package com.bsuir.dtos;

import com.bsuir.dtos.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TrailerTemplateDto {

	private Long id;

	@JsonView(View.Save.class)
	@NotBlank(message = "Trailer name cannot be blank")
	private String trailerName;

	@JsonView(View.Save.class)
	@NotBlank(message = "Code ID cannot be blank")
	private String codeId;
}
