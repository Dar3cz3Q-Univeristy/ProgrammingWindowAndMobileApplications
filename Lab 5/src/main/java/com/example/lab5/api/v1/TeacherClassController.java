package com.example.lab5.api.v1;

import com.example.lab5.dto.create.TeacherClassCreateDTO;
import com.example.lab5.dto.get.TeacherClassDTO;
import com.example.lab5.service.TeacherClassService;
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
@RequestMapping("/classes")
public class TeacherClassController {

    private final TeacherClassService teacherClassService;

    @GetMapping
    private List<TeacherClassDTO> getAllClasses() {
        return teacherClassService.findAll();
    }

    @GetMapping("/{id}")
    private TeacherClassDTO getClassById(@PathVariable("id") UUID id) {
        return teacherClassService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private TeacherClassDTO createClass(@Valid @RequestBody TeacherClassCreateDTO teacherClassCreateDTO) {
        return teacherClassService.save(teacherClassCreateDTO);
    }

    @PutMapping("/{id}")
    private TeacherClassDTO updateClass(@PathVariable("id") UUID id, @Valid @RequestBody TeacherClassCreateDTO teacherClassCreateDTO) {
        return teacherClassService.update(id, teacherClassCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteClass(@PathVariable("id") UUID id) {
        teacherClassService.delete(id);
    }

    @GetMapping("/csv")
    private ResponseEntity<InputStreamResource> exportToCSV() {
        String csvData = teacherClassService.generateCSV();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=classes.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(inputStream));
    }
}
