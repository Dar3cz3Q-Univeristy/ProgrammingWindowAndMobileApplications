package com.example.lab5.service;

import com.example.lab5.dto.create.TeacherClassCreateDTO;
import com.example.lab5.dto.get.TeacherClassDTO;
import com.example.lab5.exception.ItemNotFoundException;
import com.example.lab5.exception.SomethingWentWrongException;
import com.example.lab5.mapper.TeacherClassCreateMapper;
import com.example.lab5.mapper.TeacherClassMapper;
import com.example.lab5.model.TeacherClass;
import com.example.lab5.repository.TeacherClassRepository;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherClassService {

    private final TeacherClassRepository teacherClassRepository;
    private final TeacherClassMapper teacherClassMapper;
    private final TeacherClassCreateMapper teacherClassCreateMapper;

    public List<TeacherClassDTO> findAll() {
        List<TeacherClass> classes = teacherClassRepository.findAll();
        return classes.stream().map(teacherClassMapper::toDTO).collect(Collectors.toList());
    }

    public TeacherClassDTO findById(UUID id) {
        TeacherClass foundClass = teacherClassRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, id));
        return teacherClassMapper.toDTO(foundClass);
    }

    public TeacherClassDTO save(TeacherClassCreateDTO teacherClassCreateDTO) {
        TeacherClass teacherClass = teacherClassCreateMapper.toEntity(teacherClassCreateDTO);
        return teacherClassMapper.toDTO(teacherClassRepository.save(teacherClass));
    }

    public TeacherClassDTO update(UUID id, TeacherClassCreateDTO teacherClassCreateDTO) {
        Optional<TeacherClass> existingClass = teacherClassRepository.findById(id);
        if (existingClass.isEmpty()) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, id);

        teacherClassCreateMapper.updateTeacherClassFromDTO(teacherClassCreateDTO, existingClass.get());

        return teacherClassMapper.toDTO(teacherClassRepository.save(existingClass.get()));
    }

    public void delete(UUID id) {
        teacherClassRepository.deleteById(id);
    }

    public String generateCSV() {
        List<TeacherClass> teacherClasses = teacherClassRepository.findAll();

        StringWriter stringWriter = new StringWriter();
        try (CSVWriter writer = new CSVWriter(stringWriter)) {
            writer.writeNext(new String[]{"ID", "Name", "Capacity"});

            for (TeacherClass teacherClass : teacherClasses) {
                writer.writeNext(new String[]{
                        teacherClass.getId().toString(),
                        teacherClass.getName(),
                        Integer.toString(teacherClass.getCapacity())
                });
            }
        } catch (Exception e) {
            throw new SomethingWentWrongException();
        }

        return stringWriter.toString();
    }
}
