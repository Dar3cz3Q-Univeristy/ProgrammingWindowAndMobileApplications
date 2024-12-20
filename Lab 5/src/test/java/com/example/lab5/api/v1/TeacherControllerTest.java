package com.example.lab5.api.v1;

import com.example.lab5.dto.create.TeacherCreateDTO;
import com.example.lab5.dto.get.TeacherDTO;
import com.example.lab5.exception.ItemNotFoundException;
import com.example.lab5.service.TeacherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherController.class)
@TestPropertySource(properties = "server.servlet.context-path=/api/v1")
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllTeachers() throws Exception {
        // Given
        TeacherDTO teacher1 = new TeacherDTO(
                "a1b2c3d4-5678-90ab-cdef-1234567890ab",
                "John",
                "Doe",
                "Active",
                new Date(124, Calendar.JANUARY, 10),
                5000.00,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"
        );
        TeacherDTO teacher2 = new TeacherDTO(
                "b2c3d4e5-6789-0abc-def1-234567890abc",
                "Jane",
                "Smith",
                "On Leave",
                new Date(124, Calendar.FEBRUARY, 20),
                4500.00,
                "268f9f0a-f184-41c1-974c-f785eeff1dd5"
        );
        List<TeacherDTO> teachers = Arrays.asList(teacher1, teacher2);

        // When
        when(teacherService.findAll()).thenReturn(teachers);

        // Then
        mockMvc.perform(get("/teachers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("a1b2c3d4-5678-90ab-cdef-1234567890ab"))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].surname").value("Doe"))
                .andExpect(jsonPath("$[0].condition").value("Active"))
                .andExpect(jsonPath("$[0].birthday").value("2024-01-09T23:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].salary").value(5000.00))
                .andExpect(jsonPath("$[0].groupID").value("1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"))
                .andExpect(jsonPath("$[1].id").value("b2c3d4e5-6789-0abc-def1-234567890abc"))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[1].surname").value("Smith"))
                .andExpect(jsonPath("$[1].condition").value("On Leave"))
                .andExpect(jsonPath("$[1].birthday").value("2024-02-19T23:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].salary").value(4500.00))
                .andExpect(jsonPath("$[1].groupID").value("268f9f0a-f184-41c1-974c-f785eeff1dd5"));

        verify(teacherService, times(1)).findAll();
    }

    @Test
    public void testGetTeacherById() throws Exception {
        // Given
        String teacherId = "a1b2c3d4-5678-90ab-cdef-1234567890ab";
        TeacherDTO teacher = new TeacherDTO(
                teacherId,
                "John",
                "Doe",
                "Active",
                new Date(124, Calendar.JANUARY, 10),
                5000.00,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"
        );

        // When
        UUID uuid = UUID.fromString(teacherId);
        when(teacherService.findById(uuid)).thenReturn(teacher);

        // Then
        mockMvc.perform(get("/teachers/{id}", teacherId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teacherId))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.surname").value("Doe"))
                .andExpect(jsonPath("$.condition").value("Active"))
                .andExpect(jsonPath("$.birthday").value("2024-01-09T23:00:00.000+00:00"))
                .andExpect(jsonPath("$.salary").value(5000.00))
                .andExpect(jsonPath("$.groupID").value("1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"));

        verify(teacherService, times(1)).findById(uuid);
    }

    @Test
    public void testGetTeacherByGroupId() throws Exception {
        // Given
        String groupId = "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296";
        TeacherDTO teacher1 = new TeacherDTO(
                "c3d4e5f6-7890-abcd-ef12-34567890abcd",
                "Alice",
                "Johnson",
                "Active",
                new Date(124, Calendar.MARCH, 15),
                4800.00,
                groupId
        );
        TeacherDTO teacher2 = new TeacherDTO(
                "d4e5f6g7-890a-bcde-f123-4567890abcde",
                "Bob",
                "Williams",
                "On Leave",
                new Date(124, Calendar.APRIL, 25),
                4700.00,
                groupId
        );
        List<TeacherDTO> teachers = Arrays.asList(teacher1, teacher2);

        // When
        UUID uuid = UUID.fromString(groupId);
        when(teacherService.findByGroupId(uuid)).thenReturn(teachers);

        // Then
        mockMvc.perform(get("/teachers/group/{id}", groupId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("c3d4e5f6-7890-abcd-ef12-34567890abcd"))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].surname").value("Johnson"))
                .andExpect(jsonPath("$[0].condition").value("Active"))
                .andExpect(jsonPath("$[0].birthday").value("2024-03-14T23:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].salary").value(4800.00))
                .andExpect(jsonPath("$[0].groupID").value(groupId))
                .andExpect(jsonPath("$[1].id").value("d4e5f6g7-890a-bcde-f123-4567890abcde"))
                .andExpect(jsonPath("$[1].name").value("Bob"))
                .andExpect(jsonPath("$[1].surname").value("Williams"))
                .andExpect(jsonPath("$[1].condition").value("On Leave"))
                .andExpect(jsonPath("$[1].birthday").value("2024-04-24T22:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].salary").value(4700.00))
                .andExpect(jsonPath("$[1].groupID").value(groupId));

        verify(teacherService, times(1)).findByGroupId(uuid);
    }

    @Test
    public void testCreateTeacher() throws Exception {
        // Given
        TeacherCreateDTO createDTO = new TeacherCreateDTO(
                "Alice",
                "Johnson",
                "OBECNY",
                new Date(124, Calendar.MARCH, 15),
                4800.00,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"
        );
        TeacherDTO savedTeacher = new TeacherDTO(
                "c3d4e5f6-7890-abcd-ef12-34567890abcd",
                "Alice",
                "Johnson",
                "OBECNY",
                new Date(124, Calendar.MARCH, 15),
                4800.00,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"
        );

        // When
        when(teacherService.save(any(TeacherCreateDTO.class))).thenReturn(savedTeacher);

        String jsonContent = objectMapper.writeValueAsString(createDTO);

        // Then
        mockMvc.perform(post("/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("c3d4e5f6-7890-abcd-ef12-34567890abcd"))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Johnson"))
                .andExpect(jsonPath("$.condition").value("OBECNY"))
                .andExpect(jsonPath("$.birthday").value("2024-03-14T23:00:00.000+00:00"))
                .andExpect(jsonPath("$.salary").value(4800.00))
                .andExpect(jsonPath("$.groupID").value("1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"));

        verify(teacherService, times(1)).save(any(TeacherCreateDTO.class));
    }

    @Test
    public void testUpdateTeacher() throws Exception {
        // Given
        String teacherId = "c3d4e5f6-7890-abcd-ef12-34567890abcd";
        UUID uuid = UUID.fromString(teacherId);
        TeacherCreateDTO updateDTO = new TeacherCreateDTO(
                "Alice",
                "Williams",
                "NIEOBECNY",
                new Date(124, Calendar.MARCH, 15),
                4700.00,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"
        );
        TeacherDTO updatedTeacher = new TeacherDTO(
                teacherId,
                "Alice",
                "Williams",
                "NIEOBECNY",
                new Date(124, Calendar.MARCH, 15),
                4700.00,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"
        );

        // When
        when(teacherService.update(eq(uuid), any(TeacherCreateDTO.class))).thenReturn(updatedTeacher);

        String jsonContent = objectMapper.writeValueAsString(updateDTO);

        // Then
        mockMvc.perform(put("/teachers/{id}", teacherId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(teacherId))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.surname").value("Williams"))
                .andExpect(jsonPath("$.condition").value("NIEOBECNY"))
                .andExpect(jsonPath("$.birthday").value("2024-03-14T23:00:00.000+00:00"))
                .andExpect(jsonPath("$.salary").value(4700.00))
                .andExpect(jsonPath("$.groupID").value("1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"));

        verify(teacherService, times(1)).update(eq(uuid), any(TeacherCreateDTO.class));
    }

    @Test
    public void testDeleteTeacher() throws Exception {
        // Given
        String teacherId = "c3d4e5f6-7890-abcd-ef12-34567890abcd";
        UUID uuid = UUID.fromString(teacherId);

        // When
        doNothing().when(teacherService).delete(uuid);

        // Then
        mockMvc.perform(delete("/teachers/{id}", teacherId))
                .andExpect(status().isNoContent());

        verify(teacherService, times(1)).delete(uuid);
    }

    @Test
    public void testDeleteTeacher_NotFound() throws Exception {
        // Given
        UUID uuid = UUID.fromString("d4e5f6f7-890a-bcde-f123-4567890abcde");

        // When
        doThrow(new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER, uuid))
                .when(teacherService).delete(uuid);

        // Then
        mockMvc.perform(delete("/teachers/{id}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Item TEACHER with id: "+ uuid +" doesn't exist"));

        verify(teacherService, times(1)).delete(uuid);
    }

    @Test
    public void testExportToCSV() throws Exception {
        // Given
        String csvData = """
                id,name,surname,condition,birthday,salary,groupID
                c3d4e5f6-7890-abcd-ef12-34567890abcd,Alice,Williams,On Leave,2024-03-15,4700.00,1c4cdba2-d4bb-4a1c-93eb-5fa38e180296
                d4e5f6g7-890a-bcde-f123-4567890abcde,Bob,Smith,Active,2024-04-20,4500.00,268f9f0a-f184-41c1-974c-f785eeff1dd5""";

        // When
        when(teacherService.generateCSV()).thenReturn(csvData);

        // Then
        mockMvc.perform(get("/teachers/csv")
                        .accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=teachers.csv"))
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string(csvData));

        verify(teacherService, times(1)).generateCSV();
    }

    @Test
    public void testCreateTeacher_InvalidInput() throws Exception {
        // Given
        String invalidJsonContent = "{ \"surname\": \"Johnson\", \"condition\": \"Active\", \"birthday\": \"2024-03-15\", \"groupID\": \"1c4cdba2-d4bb-4a1c-93eb-5fa38e180296\" }";

        // Then
        mockMvc.perform(post("/teachers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonContent))
                .andExpect(status().isBadRequest());

        verify(teacherService, times(0)).save(any(TeacherCreateDTO.class));
    }
}
