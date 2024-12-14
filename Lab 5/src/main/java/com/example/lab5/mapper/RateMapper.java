package com.example.lab5.mapper;

import com.example.lab5.dto.get.RateDTO;
import com.example.lab5.model.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperHelper.class)
public interface RateMapper {

    @Mapping(source = "group.id", target = "groupID")
    RateDTO toDTO(Rate rate);

    @Mapping(source = "groupID", target = "group", qualifiedByName = "mapGroupIdToGroup")
    Rate toEntity(RateDTO rateDTO);
}
