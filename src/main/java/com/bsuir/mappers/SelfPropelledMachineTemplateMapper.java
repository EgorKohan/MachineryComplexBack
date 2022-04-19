package com.bsuir.mappers;

import com.bsuir.dtos.SelfPropelledMachineTemplateDto;
import com.bsuir.models.SelfPropelledMachineTemplate;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SelfPropelledMachineTemplateMapper {

    SelfPropelledMachineTemplate toTemplate(SelfPropelledMachineTemplateDto dto);

    SelfPropelledMachineTemplateDto toDto(SelfPropelledMachineTemplate template);

    List<SelfPropelledMachineTemplateDto> toDtos(Collection<SelfPropelledMachineTemplate> templates);

}
