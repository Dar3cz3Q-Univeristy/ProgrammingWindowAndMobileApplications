package com.example.lab5.dto.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDTO {
    private String id;
    private String name;
    private String surname;
    private String condition;
    private Date birthday;
    private double salary;
    private String groupID;
}
