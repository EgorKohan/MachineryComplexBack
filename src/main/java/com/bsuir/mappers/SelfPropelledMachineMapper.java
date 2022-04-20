package com.bsuir.mappers;

import com.bsuir.dtos.SelfPropelledMachineDto;
import com.bsuir.models.SelfPropelledMachine;
import com.bsuir.models.SelfPropelledMachineTemplate;
import com.bsuir.services.SelfPropelledMachineTemplateService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SelfPropelledMachineMapper {

    @Autowired
    protected SelfPropelledMachineTemplateService templateService;

    @BeforeMapping
    protected void setMachineTemplate(@MappingTarget SelfPropelledMachine machine, SelfPropelledMachineDto dto) {
        SelfPropelledMachineTemplate template = templateService.findById(dto.getMachineTemplateId());
        machine.setMachineTemplate(template);
    }

    @Mapping(target = "machineTemplate", ignore = true)
    public abstract SelfPropelledMachine toMachine(SelfPropelledMachineDto dto);

    @Mapping(target = "machineTemplateId", source = "machine.machineTemplate.id")
    public abstract SelfPropelledMachineDto toDto(SelfPropelledMachine machine);

    public abstract List<SelfPropelledMachineDto> toDtos(List<SelfPropelledMachine> machines);

    public abstract List<SelfPropelledMachine> toMachines(List<SelfPropelledMachineDto> dtos);
}
