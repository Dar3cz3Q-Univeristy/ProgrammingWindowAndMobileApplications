package org.example.lab4b.Mapper;

import org.example.lab4b.DTO.GroupStatisticsDTO;
import org.example.lab4b.Model.TeacherClass;

public class GroupStatisticsMapper {

    public static GroupStatisticsDTO toDTO(TeacherClass group, int teachersCount, Long rateCount, double rateAvg) {
        double fill = 0.0;

        if (group.getCapacity() > 0) {
            fill = (teachersCount * 100.0) / group.getCapacity();
        }

        return new GroupStatisticsDTO(group.getName(), group.getCapacity(), fill, rateCount, rateAvg);
    }
}
