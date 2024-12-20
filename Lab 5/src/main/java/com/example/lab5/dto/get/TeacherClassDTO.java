package com.example.lab5.dto.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherClassDTO {
    private String id;
    private String name;
    private int capacity;
    private double fillPercentage;
    private double averageRating;
}
