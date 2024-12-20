package com.example.lab5.api.v1;

import com.example.lab5.dto.get.TeacherDTO;
import com.example.lab5.dto.create.TeacherCreateDTO;
import com.example.lab5.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherDTO getTeacherById(@PathVariable("id") UUID id) {
        return teacherService.findById(id);
    }

    @GetMapping("/group/{id}")
    public List<TeacherDTO> getTeacherByGroupId(@PathVariable("id") UUID id) {
        return teacherService.findByGroupId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO createTeacher(@Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        return teacherService.save(teacherCreateDTO);
    }

    @PutMapping("/{id}")
    public TeacherDTO updateTeacher(@PathVariable("id") UUID id, @Valid @RequestBody TeacherCreateDTO teacherCreateDTO) {
        return teacherService.update(id, teacherCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacher(@PathVariable("id") UUID id) {
        teacherService.delete(id);
    }

    @GetMapping("/csv")
    public ResponseEntity<InputStreamResource> exportToCSV() {
        String csvData = teacherService.generateCSV();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teachers.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(inputStream));
    }
}
