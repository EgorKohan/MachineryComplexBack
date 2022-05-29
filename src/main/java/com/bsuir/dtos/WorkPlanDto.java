package com.bsuir.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

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

    @NotNull(message = "Periods cannot be null")
    @NotEmpty(message = "Periods must have at least 1 element")
    private Set<PeriodDto> periods;

}
