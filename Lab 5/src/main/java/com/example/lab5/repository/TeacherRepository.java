package com.example.lab5.repository;

import com.example.lab5.model.Teacher;
import com.example.lab5.model.TeacherClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    List<Teacher> findTeacherByGroup(TeacherClass teacherClass);
}
