package com.example.lab5.mapper;

import com.example.lab5.dto.get.TeacherDTO;
import com.example.lab5.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MapperHelper.class)
public interface TeacherMapper {

    @Mapping(source = "group.id", target = "groupID")
    TeacherDTO toDTO(Teacher teacher);

    @Mapping(source = "groupID", target = "group", qualifiedByName = "mapGroupIdToGroup")
    Teacher toEntity(TeacherDTO teacherDTO);
}
