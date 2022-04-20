package com.bsuir.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SelfPropelledMachineTemplateDto {

    private Long id;

    @NotBlank(message = "Machine name cannot be blank")
    private String machineName;

    @NotBlank(message = "Code ID cannot be blank")
    private String codeId;
}
