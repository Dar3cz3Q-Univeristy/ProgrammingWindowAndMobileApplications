package com.example.lab5.mapper;

import com.example.lab5.dto.create.TeacherClassCreateDTO;
import com.example.lab5.model.TeacherClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = MapperHelper.class)
public interface TeacherClassCreateMapper {

    TeacherClassCreateDTO toDTO(TeacherClass teacherClass);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "rates",  ignore = true)
    TeacherClass toEntity(TeacherClassCreateDTO teacherClassCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "rates",  ignore = true)
    void updateTeacherClassFromDTO(TeacherClassCreateDTO teacherClassCreateDTO, @MappingTarget TeacherClass teacherClass);
}
