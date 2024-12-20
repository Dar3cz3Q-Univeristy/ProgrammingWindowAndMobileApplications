package com.example.lab5.api.v1;

import com.example.lab5.dto.create.TeacherClassCreateDTO;
import com.example.lab5.dto.get.TeacherClassDTO;
import com.example.lab5.exception.ItemNotFoundException;
import com.example.lab5.repository.TeacherClassRepository;
import com.example.lab5.service.TeacherClassService;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeacherClassController.class)
@TestPropertySource(properties = "server.servlet.context-path=/api/v1")
public class TeacherClassControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherClassService teacherClassService;

    @MockBean
    private TeacherClassRepository teacherClassRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllClasses() throws Exception {
        // Given
        TeacherClassDTO class1 = new TeacherClassDTO(
                "a1b2c3d4-5678-90ab-cdef-1234567890ab",
                "Mathematics",
                30,
                75.0,
                4.5
        );
        TeacherClassDTO class2 = new TeacherClassDTO(
                "b2c3d4e5-6789-0abc-def1-234567890abc",
                "Physics",
                25,
                60.0,
                4.0
        );
        List<TeacherClassDTO> classes = Arrays.asList(class1, class2);

        // When
        when(teacherClassService.findAll()).thenReturn(classes);

        // Then
        mockMvc.perform(get("/classes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("a1b2c3d4-5678-90ab-cdef-1234567890ab"))
                .andExpect(jsonPath("$[0].name").value("Mathematics"))
                .andExpect(jsonPath("$[0].capacity").value(30))
                .andExpect(jsonPath("$[0].fillPercentage").value(75.0))
                .andExpect(jsonPath("$[0].averageRating").value(4.5))
                .andExpect(jsonPath("$[1].id").value("b2c3d4e5-6789-0abc-def1-234567890abc"))
                .andExpect(jsonPath("$[1].name").value("Physics"))
                .andExpect(jsonPath("$[1].capacity").value(25))
                .andExpect(jsonPath("$[1].fillPercentage").value(60.0))
                .andExpect(jsonPath("$[1].averageRating").value(4.0));

        verify(teacherClassService, times(1)).findAll();
    }

    @Test
    public void testGetClassById() throws Exception {
        // Given
        String classId = "a1b2c3d4-5678-90ab-cdef-1234567890ab";
        TeacherClassDTO teacherClass = new TeacherClassDTO(
                classId,
                "Mathematics",
                30,
                75.0,
                4.5
        );

        // When
        UUID uuid = UUID.fromString(classId);
        when(teacherClassService.findById(uuid)).thenReturn(teacherClass);

        // Then
        mockMvc.perform(get("/classes/{id}", classId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(classId))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.capacity").value(30))
                .andExpect(jsonPath("$.fillPercentage").value(75.0))
                .andExpect(jsonPath("$.averageRating").value(4.5));

        verify(teacherClassService, times(1)).findById(uuid);
    }

    @Test
    public void testCreateClass() throws Exception {
        // Given
        TeacherClassCreateDTO createDTO = new TeacherClassCreateDTO(
                "Chemistry",
                20
        );
        TeacherClassDTO savedClass = new TeacherClassDTO(
                "c3d4e5f6-7890-abcd-ef12-34567890abcd",
                "Chemistry",
                20,
                50.0,
                4.2
        );

        // When
        when(teacherClassService.save(any(TeacherClassCreateDTO.class))).thenReturn(savedClass);

        String jsonContent = objectMapper.writeValueAsString(createDTO);

        // Then
        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("c3d4e5f6-7890-abcd-ef12-34567890abcd"))
                .andExpect(jsonPath("$.name").value("Chemistry"))
                .andExpect(jsonPath("$.capacity").value(20))
                .andExpect(jsonPath("$.fillPercentage").value(50.0))
                .andExpect(jsonPath("$.averageRating").value(4.2));

        verify(teacherClassService, times(1)).save(any(TeacherClassCreateDTO.class));
    }

    @Test
    public void testUpdateClass() throws Exception {
        // Given
        String classId = "c3d4e5f6-7890-abcd-ef12-34567890abcd";
        UUID uuid = UUID.fromString(classId);
        TeacherClassCreateDTO updateDTO = new TeacherClassCreateDTO(
                "Advanced Chemistry",
                25
        );
        TeacherClassDTO updatedClass = new TeacherClassDTO(
                classId,
                "Advanced Chemistry",
                25,
                60.0,
                4.3
        );

        // When
        when(teacherClassService.update(eq(uuid), any(TeacherClassCreateDTO.class))).thenReturn(updatedClass);

        String jsonContent = objectMapper.writeValueAsString(updateDTO);

        // Then
        mockMvc.perform(put("/classes/{id}", classId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(classId))
                .andExpect(jsonPath("$.name").value("Advanced Chemistry"))
                .andExpect(jsonPath("$.capacity").value(25))
                .andExpect(jsonPath("$.fillPercentage").value(60.0))
                .andExpect(jsonPath("$.averageRating").value(4.3));

        verify(teacherClassService, times(1)).update(eq(uuid), any(TeacherClassCreateDTO.class));
    }

    @Test
    public void testDeleteClass() throws Exception {
        // Given
        String classId = "c3d4e5f6-7890-abcd-ef12-34567890abcd";
        UUID uuid = UUID.fromString(classId);

        // When
        doNothing().when(teacherClassService).delete(uuid);

        // Then
        mockMvc.perform(delete("/classes/{id}", classId))
                .andExpect(status().isNoContent());

        verify(teacherClassService, times(1)).delete(uuid);
    }

    @Test
    public void testDeleteClass_NotFound() throws Exception {
        // Given
        UUID uuid = UUID.fromString("d4e5f6a7-890a-bcde-f123-4567890abcdf");

        // When
        doThrow(new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, uuid))
                .when(teacherClassService).delete(uuid);

        // Then
        mockMvc.perform(delete("/classes/{id}", uuid))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Item TEACHER_CLASS with id: "+ uuid +" doesn't exist"));

        verify(teacherClassService, times(1)).delete(uuid);
    }

    @Test
    public void testExportToCSV() throws Exception {
        // Given
        String csvData = """
                id,name,capacity,fillPercentage,averageRating
                c3d4e5f6-7890-abcd-ef12-34567890abcd,Chemistry,20,50.0,4.2
                d4e5f6a7-890a-bcde-f123-4567890abcdf,Advanced Physics,25,60.0,4.3""";

        // When
        when(teacherClassService.generateCSV()).thenReturn(csvData);

        // Then
        mockMvc.perform(get("/classes/csv")
                        .accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=classes.csv"))
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string(csvData));

        verify(teacherClassService, times(1)).generateCSV();
    }

    @Test
    public void testCreateClass_InvalidInput() throws Exception {
        // Given
        String invalidJsonContent = "{ \"capacity\": -5 }";

        // Then
        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonContent))
                .andExpect(status().isBadRequest());

        verify(teacherClassService, times(0)).save(any(TeacherClassCreateDTO.class));
    }
}
