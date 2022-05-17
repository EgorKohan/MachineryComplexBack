package com.bsuir.mappers;

import com.bsuir.dtos.TrailerDto;
import com.bsuir.models.Trailer;
import com.bsuir.models.TrailerTemplate;
import com.bsuir.services.TrailerTemplateService;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TrailerMapper {

	@Autowired
	protected TrailerTemplateService trailerTemplateService;

	@BeforeMapping
	protected void setTrailerTemplate(@MappingTarget Trailer trailer, TrailerDto trailerDto) {
		TrailerTemplate trailerTemplate = trailerTemplateService.findById(trailerDto.getTrailerTemplateId());
		trailer.setTrailerTemplate(trailerTemplate);
	}

	@Mapping(target = "trailerTemplate", ignore = true)
	@Mapping(target = "pathToPhoto", ignore = true)
	public abstract Trailer toTrailer(TrailerDto dto);

	@Mapping(target = "trailerTemplateId", source = "trailer.trailerTemplate.id")
	public abstract TrailerDto toDto(Trailer trailer);

	public abstract List<TrailerDto> toDtos(List<Trailer> trailers);

	public abstract List<Trailer> toTrailers(List<TrailerDto> dtos);
}
