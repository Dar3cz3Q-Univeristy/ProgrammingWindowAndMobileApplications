package com.example.lab5.api.v1;

import com.example.lab5.dto.create.RateCreateDTO;
import com.example.lab5.dto.get.RateDTO;
import com.example.lab5.service.RateService;
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
@RequestMapping("/rates")
public class RateController {

    private final RateService rateService;

    @GetMapping
    public List<RateDTO> getAllRates() {
        return rateService.findAll();
    }

    @GetMapping("/{id}")
    public RateDTO getRateById(@PathVariable("id") UUID id) {
        return rateService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RateDTO createRate(@Valid @RequestBody RateCreateDTO rateCreateDTO) {
        return rateService.save(rateCreateDTO);
    }

    @PutMapping("/{id}")
    public RateDTO updateRate(@PathVariable("id") UUID id, @Valid @RequestBody RateCreateDTO rateCreateDTO) {
        return rateService.update(id, rateCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRate(@PathVariable("id") UUID id) {
        rateService.delete(id);
    }

    @GetMapping("/csv")
    public ResponseEntity<InputStreamResource> exportToCSV() {
        String csvData = rateService.generateCSV();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(csvData.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rates.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(inputStream));
    }

}
