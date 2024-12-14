package com.example.lab5.service;

import com.example.lab5.dto.get.TeacherDTO;
import com.example.lab5.dto.create.TeacherCreateDTO;
import com.example.lab5.exception.SomethingWentWrongException;
import com.example.lab5.mapper.TeacherCreateMapper;
import com.example.lab5.mapper.TeacherMapper;
import com.example.lab5.model.Teacher;
import com.example.lab5.model.TeacherClass;
import com.example.lab5.repository.TeacherClassRepository;
import com.example.lab5.repository.TeacherRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.lab5.exception.ItemNotFoundException;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherClassRepository teacherClassRepository;
    private final TeacherMapper teacherMapper;
    private final TeacherCreateMapper teacherCreateMapper;

    public List<TeacherDTO> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream().map(teacherMapper::toDTO).collect(Collectors.toList());
    }

    public TeacherDTO findById(UUID id) {
        Teacher foundTeacher = teacherRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER, id));
        return teacherMapper.toDTO(foundTeacher);
    }

    public List<TeacherDTO> findByGroupId(UUID groupId) {
        Optional<TeacherClass> teacherClass = teacherClassRepository.findById(groupId);
        if (teacherClass.isEmpty()) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, groupId);

        List<Teacher> teachers = teacherRepository.findTeacherByGroup(teacherClass.get());

        return teachers.stream().map(teacherMapper::toDTO).collect(Collectors.toList());
    }

    public TeacherDTO save(TeacherCreateDTO teacherCreateDTO) {
        UUID groupID = UUID.fromString(teacherCreateDTO.getGroupID());
        if (!teacherClassRepository.existsById(groupID)) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, groupID);

        Teacher teacher = teacherCreateMapper.toEntity(teacherCreateDTO);
        return teacherMapper.toDTO(teacherRepository.save(teacher));
    }

    public TeacherDTO update(UUID id, TeacherCreateDTO teacherCreateDTO) {
        Optional<Teacher> existingTeacher = teacherRepository.findById(id);
        if (existingTeacher.isEmpty()) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER, id);

        UUID groupID = UUID.fromString(teacherCreateDTO.getGroupID());
        if (!teacherClassRepository.existsById(groupID)) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, groupID);

        teacherCreateMapper.updateTeacherFromDTO(teacherCreateDTO, existingTeacher.get());

        return teacherMapper.toDTO(teacherRepository.save(existingTeacher.get()));
    }

    public void delete(UUID id) {
        teacherRepository.deleteById(id);
    }

    public String generateCSV() {
        List<Teacher> teachers = teacherRepository.findAll();

        StringWriter stringWriter = new StringWriter();
        try (CSVWriter writer = new CSVWriter(stringWriter)) {
            writer.writeNext(new String[]{"ID", "Name", "Surname", "Condition", "Birthday", "Salary", "GroupID"});

            for (Teacher teacher : teachers) {
                writer.writeNext(new String[]{
                        teacher.getId().toString(),
                        teacher.getName(),
                        teacher.getSurname(),
                        teacher.getCondition().toString(),
                        teacher.getBirthday().toString(),
                        Double.toString(teacher.getSalary()),
                        teacher.getGroup().getId().toString()
                });
            }
        } catch (Exception e) {
            throw new SomethingWentWrongException();
        }

        return stringWriter.toString();
    }
}
