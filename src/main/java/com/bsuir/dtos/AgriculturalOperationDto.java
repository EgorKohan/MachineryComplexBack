package com.bsuir.dtos;

import com.bsuir.models.EUnit;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AgriculturalOperationDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Work volume cannot be null")
    @Min(value = 0, message = "Work volume cannot be less than 0")
    private Float workVolume;

    @NotNull(message = "Unit cannot be null")
    private EUnit unit;

    @NotNull(message = "Start date cannot be null")
    private LocalDateTime startDate;

    @NotNull(message = "End date cannot be null")
    private LocalDateTime endDate;
}
