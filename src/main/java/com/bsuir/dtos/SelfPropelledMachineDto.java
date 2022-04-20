package com.bsuir.dtos;

import com.bsuir.dtos.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SelfPropelledMachineDto {

    private Long id;

    @JsonView(View.Save.class)
    @NotBlank(message = "Inventory number cannot be empty or null")
    private String inventoryNumber;

    @JsonView(View.Save.class)
    @NotNull(message = "Machine template ID cannot be null")
    private Long machineTemplateId;

    @JsonView(View.Save.class)
    @NotNull(message = "Actual service life cannot be null")
    @Min(value = 0, message = "Actual service life cannot be less than 0")
    private Integer actualServiceLifeInMonth;

    @JsonView(View.Save.class)
    @NotNull(message = "Normative service life cannot be null")
    @Min(value = 0, message = "Normative service life cannot be less than 0")
    private Integer normativeServiceLifeInMonth;

    @JsonView(View.Save.class)
    @NotNull(message = "Initial cost cannot be null")
    @Min(value = 0, message = "Initial cost cannot be less than 0")
    private BigDecimal initialCost;

    @JsonView(View.Save.class)
    @NotNull(message = "Residual value cannot be null")
    @Min(value = 0, message = "Residual value cannot be less than 0")
    private BigDecimal residualValue;

    @JsonView(View.Save.class)
    @NotNull(message = "Technical readiness factor cannot be null")
    @Min(value = 0, message = "Technical readiness factor cannot be less than 0")
    @Max(value = 1, message = "Technical readiness factor cannot be more than 1")
    private Float trk;
}
