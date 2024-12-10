package org.example.lab4b.Service;

import org.example.lab4b.Model.Teacher;
import org.example.lab4b.Model.TeacherClass;
import org.example.lab4b.Repository.TeacherClassRepository;

import java.util.List;

public class TeacherClassService {

    private final TeacherClassRepository teacherClassRepository = new TeacherClassRepository();

    public TeacherClass createGroup(TeacherClass group) {
        return teacherClassRepository.save(group);
    }

    public TeacherClass getGroup(int id) {
        return teacherClassRepository.findById(id);
    }

    public List<TeacherClass> getAllGroups() {
        return teacherClassRepository.findAll();
    }

    public TeacherClass findByName(String name) {
        return teacherClassRepository.findByName(name);
    }

    public void deleteClass(TeacherClass group){
        teacherClassRepository.delete(group);
    }
}
