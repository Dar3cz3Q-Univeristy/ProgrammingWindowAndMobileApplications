package com.example.lab5.mapper;

import com.example.lab5.dto.create.RateCreateDTO;
import com.example.lab5.model.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = MapperHelper.class)
public interface RateCreateMapper {

    @Mapping(source = "group.id", target = "groupID")
    RateCreateDTO toDTO(Rate rate);

    @Mapping(source = "groupID", target = "group", qualifiedByName = "mapGroupIdToGroup")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    Rate toEntity(RateCreateDTO rateCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "date", ignore = true)
    void updateRateFromDTO(RateCreateDTO rateCreateDTO, @MappingTarget Rate rate);
}
