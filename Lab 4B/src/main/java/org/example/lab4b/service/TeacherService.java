package org.example.lab4b.Service;
import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Repository.TeacherRepository;

import java.util.List;

public class TeacherService {

    private final TeacherRepository teacherRepository = new TeacherRepository();

    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher getTeacher(int id) {
        return teacherRepository.findById(id);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public List<Teacher> getTeachersByGroup(String group) {
        return teacherRepository.findByGroup(group);
    }

    public int countByGroup(String group) {
        return teacherRepository.countByGroup(group);
    }

    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.update(teacher);
    }

    public void deleteTeacher(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    public List<Teacher> getTeachersByGroupAndName(String group, String name) {
        return teacherRepository.findByGroupFilteredByLastName(group, name);
    }
}
