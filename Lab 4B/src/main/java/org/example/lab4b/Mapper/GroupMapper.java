package org.example.lab4b.Mapper;

import org.example.lab4b.DTO.GroupFillDTO;
import org.example.lab4b.Model.TeacherClass;

public class GroupMapper {

    public static GroupFillDTO toDTO(TeacherClass group, int teachersCount) {
        double fill = 0.0;

        if (group.getCapacity() > 0) {
            fill = (teachersCount * 100.0) / group.getCapacity();
        }

        return new GroupFillDTO(group.getName(), group.getCapacity(), fill);
    }
}
