package com.bsuir.mappers;

import com.bsuir.dtos.PeriodDto;
import com.bsuir.models.Period;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PeriodMapper {

    PeriodDto toDto(Period period);

}
