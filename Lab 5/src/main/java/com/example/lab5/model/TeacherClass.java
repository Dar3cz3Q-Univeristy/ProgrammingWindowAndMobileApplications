package com.example.lab5.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TeacherClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rate> rates;
    @Column(unique = true)
    private String name;
    private int capacity;
}
