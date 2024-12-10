package org.example.lab4b.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @NonNull
    private TeacherClass group;

    @Column
    @NonNull
    private String name;

    @Column
    @NonNull
    private String surname;

    @Enumerated(EnumType.STRING)
    @NonNull
    private TeacherCondition condition;

    @Column
    @NonNull
    private int yearOfBirth;

    @Column
    @NonNull
    private double salary;
}
