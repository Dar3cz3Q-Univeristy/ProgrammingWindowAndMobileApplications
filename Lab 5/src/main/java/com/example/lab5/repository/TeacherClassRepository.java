package com.example.lab5.repository;

import com.example.lab5.model.TeacherClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TeacherClassRepository extends JpaRepository<TeacherClass, UUID> {
    boolean existsByName(String value);
}
