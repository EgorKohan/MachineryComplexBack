package com.bsuir.mappers;

import com.bsuir.dtos.TrailerTemplateDto;
import com.bsuir.models.TrailerTemplate;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TrailerTemplateMapper {
	TrailerTemplate toTemplate(TrailerTemplateDto dto);

	TrailerTemplateDto toDto(TrailerTemplate template);

	List<TrailerTemplateDto> toDtos(Collection<TrailerTemplate> templates);

	List<TrailerTemplate> toTemplates(Collection<TrailerTemplateDto> dtos);
}
