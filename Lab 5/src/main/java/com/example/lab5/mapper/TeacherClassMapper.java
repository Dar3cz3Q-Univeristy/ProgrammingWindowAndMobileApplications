package com.example.lab5.mapper;

import com.example.lab5.dto.get.TeacherClassDTO;
import com.example.lab5.model.Rate;
import com.example.lab5.model.TeacherClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeacherClassMapper {

    @Mapping(target = "fillPercentage", expression = "java(com.example.lab5.mapper.TeacherClassMapper.calculateFillPercentage(teacherClass))")
    @Mapping(target = "averageRating", expression = "java(com.example.lab5.mapper.TeacherClassMapper.calculateAverageRating(teacherClass))")
    TeacherClassDTO toDTO(TeacherClass teacherClass);

    @Mapping(target = "teachers", ignore = true)
    @Mapping(target = "rates", ignore = true)
    TeacherClass toEntity(TeacherClassDTO teacherClassDTO);

    static double calculateFillPercentage(TeacherClass teacherClass) {
        if (teacherClass.getTeachers() == null || teacherClass.getCapacity() <= 0) {
            return 0;
        }
        return ((double) teacherClass.getTeachers().size() / teacherClass.getCapacity()) * 100;
    }

    static double calculateAverageRating(TeacherClass teacherClass) {
        if (teacherClass.getRates() == null || teacherClass.getRates().isEmpty()) {
            return 0;
        }
        return teacherClass.getRates().stream()
                .mapToDouble(Rate::getRate)
                .average()
                .orElse(0);
    }
}
