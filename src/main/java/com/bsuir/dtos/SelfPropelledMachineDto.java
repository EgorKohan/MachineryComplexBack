package com.bsuir.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SelfPropelledMachineDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Inventory number cannot be empty or null")
    private String inventoryNumber;

    @NotNull(message = "Machine template ID cannot be null")
    private Long machineTemplateId;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile photo;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String pathToPhoto;

}
