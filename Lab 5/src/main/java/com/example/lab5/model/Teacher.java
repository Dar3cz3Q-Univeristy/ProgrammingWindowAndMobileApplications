package com.example.lab5.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private TeacherClass group;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private TeacherCondition condition;
    private Date birthday;
    private double salary;
}
