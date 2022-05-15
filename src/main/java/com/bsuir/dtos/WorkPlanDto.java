package com.bsuir.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class WorkPlanDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "Machine template ID cannot be null")
    private Long machineTemplateId;

    @NotNull(message = "Trailer template ID cannot be null")
    private Long trailerTemplateId;

    @NotNull(message = "Agricultural operation ID cannot be null")
    private Long operationId;

    @NotNull(message = "Work per shift cannot be null")
    @Min(value = 0, message = "Work per shift cannot be less than 0")
    private Float workPerShift;
}
