package com.example.lab5.api.v1;

import com.example.lab5.dto.create.RateCreateDTO;
import com.example.lab5.dto.get.RateDTO;
import com.example.lab5.service.RateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RateController.class)
public class RateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RateService rateService;

    @Test
    public void testGetAllRates() throws Exception {
        // Given
        RateDTO rate1 = new RateDTO(
                "152c071a-95d1-4a6a-a4d2-f42f64729650",
                5,
                "1c4cdba2-d4bb-4a1c-93eb-5fa38e180296",
                new Date(2024 - 1900, Calendar.DECEMBER, 16),
                "Good group"
        );
        RateDTO rate2 = new RateDTO(
                "4e0fb1f8-540e-4164-a8b8-d3ba8fc46aa1",
                4,
                "268f9f0a-f184-41c1-974c-f785eeff1dd5",
                new Date(2024 - 1900, Calendar.NOVEMBER, 21),
                "Average group"
        );
        List<RateDTO> rates = Arrays.asList(rate1, rate2);

        // When
        when(rateService.findAll()).thenReturn(rates);

        // Then
        mockMvc.perform(get("/rates").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("152c071a-95d1-4a6a-a4d2-f42f64729650"))
                .andExpect(jsonPath("$[0].rate").value(5))
                .andExpect(jsonPath("$[0].groupID").value("1c4cdba2-d4bb-4a1c-93eb-5fa38e180296"))
                .andExpect(jsonPath("$[0].date").value("2024-12-15T23:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].comment").value("Good group"))
                .andExpect(jsonPath("$[1].id").value("4e0fb1f8-540e-4164-a8b8-d3ba8fc46aa1"))
                .andExpect(jsonPath("$[1].rate").value(4))
                .andExpect(jsonPath("$[1].groupID").value("268f9f0a-f184-41c1-974c-f785eeff1dd5"))
                .andExpect(jsonPath("$[1].date").value("2024-11-20T23:00:00.000+00:00"))
                .andExpect(jsonPath("$[1].comment").value("Average group"));

        verify(rateService, times(1)).findAll();
    }

    @Test
    public void testGetRateById() throws Exception {
        // Given
        String rateId = "152c071a-95d1-4a6a-a4d2-f42f64729650";
        RateDTO rate = new RateDTO(
                rateId,
                5,
                "ae284c5c-9c3a-40d0-b11a-d8b920b956f3",
                new Date(2024 - 1900, Calendar.NOVEMBER, 16),
                "Piekna grupa"
        );

        // When
        UUID uuid = UUID.fromString(rateId);
        when(rateService.findById(uuid)).thenReturn(rate);

        // Then
        mockMvc.perform(get("/rates/{id}", rateId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(rateId))
                .andExpect(jsonPath("$.rate").value(5))
                .andExpect(jsonPath("$.groupID").value("ae284c5c-9c3a-40d0-b11a-d8b920b956f3"))
                .andExpect(jsonPath("$.date").value("2024-11-15T23:00:00.000+00:00"))
                .andExpect(jsonPath("$.comment").value("Piekna grupa"));

        verify(rateService, times(1)).findById(uuid);
    }

    @Test
    public void testCreateRate() throws Exception {
        // Given
        RateDTO savedRate = new RateDTO(
                "152c071a-95d1-4a6a-a4d2-f42f64729650",
                5,
                "ae284c5c-9c3a-40d0-b11a-d8b920b956f3",
                new Date(2024 - 1900, Calendar.NOVEMBER, 16),
                "Piekna grupa"
        );

        // When
        when(rateService.save(any(RateCreateDTO.class))).thenReturn(savedRate);
        String jsonContent = "{ \"rate\": 5, \"groupID\": \"ae284c5c-9c3a-40d0-b11a-d8b920b956f3\", \"comment\": \"Piekna grupa\" }";

        // Then
        mockMvc.perform(post("/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("152c071a-95d1-4a6a-a4d2-f42f64729650"))
                .andExpect(jsonPath("$.rate").value(5))
                .andExpect(jsonPath("$.groupID").value("ae284c5c-9c3a-40d0-b11a-d8b920b956f3"))
                .andExpect(jsonPath("$.date").value("2024-11-15T23:00:00.000+00:00"))
                .andExpect(jsonPath("$.comment").value("Piekna grupa"));

        verify(rateService, times(1)).save(any(RateCreateDTO.class));
    }

    @Test
    public void testUpdateRate() throws Exception {
        // Given
        String rateId = "152c071a-95d1-4a6a-a4d2-f42f64729650";
        UUID uuid = UUID.fromString(rateId);
        RateDTO updatedRate = new RateDTO(
                rateId,
                4,
                "ae284c5c-9c3a-40d0-b11a-d8b920b956f3",
                new Date(2024 - 1900, Calendar.NOVEMBER, 16),
                "Zmieniony komentarz"
        );

        // When
        when(rateService.update(eq(uuid), any(RateCreateDTO.class))).thenReturn(updatedRate);
        String jsonContent = "{ \"rate\": 4, \"groupID\": \"ae284c5c-9c3a-40d0-b11a-d8b920b956f3\", \"comment\": \"Zmieniony komentarz\" }";

        // Then
        mockMvc.perform(put("/rates/{id}", rateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(rateId))
                .andExpect(jsonPath("$.rate").value(4))
                .andExpect(jsonPath("$.groupID").value("ae284c5c-9c3a-40d0-b11a-d8b920b956f3"))
                .andExpect(jsonPath("$.date").value("2024-11-15T23:00:00.000+00:00"))
                .andExpect(jsonPath("$.comment").value("Zmieniony komentarz"));

        verify(rateService, times(1)).update(eq(uuid), any(RateCreateDTO.class));
    }

    @Test
    public void testDeleteRate() throws Exception {
        // Given
        String rateId = "152c071a-95d1-4a6a-a4d2-f42f64729650";
        UUID uuid = UUID.fromString(rateId);

        // When
        doNothing().when(rateService).delete(uuid);

        // Then
        mockMvc.perform(delete("/rates/{id}", rateId))
                .andExpect(status().isNoContent());

        verify(rateService, times(1)).delete(uuid);
    }

    @Test
    public void testExportToCSV() throws Exception {
        // Given
        String csvData = "id,rate,groupID,date,comment\n" +
                "152c071a-95d1-4a6a-a4d2-f42f64729650,5,ae284c5c-9c3a-40d0-b11a-d8b920b956f3,2024-11-15,Piekna grupa";

        // When
        when(rateService.generateCSV()).thenReturn(csvData);

        // Then
        mockMvc.perform(get("/rates/csv")
                        .accept("text/csv"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rates.csv"))
                .andExpect(content().contentType("text/csv"))
                .andExpect(content().string(csvData));

        verify(rateService, times(1)).generateCSV();
    }

    @Test
    public void testCreateRate_InvalidInput() throws Exception {
        // Given
        String invalidJsonContent = "{ \"groupID\": \"ae284c5c-9c3a-40d0-b11a-d8b920b956f3\", \"comment\": \"Brak rate\" }";

        // Then
        mockMvc.perform(post("/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonContent))
                .andExpect(status().isBadRequest());

        verify(rateService, times(0)).save(any(RateCreateDTO.class));
    }
}
