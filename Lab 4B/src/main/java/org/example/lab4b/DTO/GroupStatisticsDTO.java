package org.example.lab4b.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GroupStatisticsDTO {

    private String name;
    private int capacity;
    private double fillPercentage;
    private Long rateCount;
    private double rateAvg;
}
