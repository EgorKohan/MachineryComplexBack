package com.bsuir.dtos;

import com.bsuir.dtos.view.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SelfPropelledMachineTemplateDto {

    private Long id;

    @JsonView(View.Save.class)
    @NotBlank(message = "Machine name cannot be blank")
    private String machineName;

    @JsonView(View.Save.class)
    @NotBlank(message = "Code ID cannot be blank")
    private String codeId;
}
