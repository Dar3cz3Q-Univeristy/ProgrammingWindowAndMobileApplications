package com.example.lab5.mapper;

import com.example.lab5.dto.create.TeacherCreateDTO;
import com.example.lab5.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = MapperHelper.class)
public interface TeacherCreateMapper {

    @Mapping(source = "group.id", target = "groupID")
    TeacherCreateDTO toDTO(Teacher teacher);

    @Mapping(source = "groupID", target = "group", qualifiedByName = "mapGroupIdToGroup")
    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherCreateDTO teacherCreateDTO);

    @Mapping(source = "groupID", target = "group", qualifiedByName = "mapGroupIdToGroup")
    @Mapping(target = "id", ignore = true)
    void updateTeacherFromDTO(TeacherCreateDTO teacherCreateDTO, @MappingTarget Teacher teacher);
}
