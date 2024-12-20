package com.example.lab5.service;

import com.example.lab5.dto.create.RateCreateDTO;
import com.example.lab5.dto.get.RateDTO;
import com.example.lab5.exception.ItemNotFoundException;
import com.example.lab5.exception.SomethingWentWrongException;
import com.example.lab5.mapper.RateCreateMapper;
import com.example.lab5.mapper.RateMapper;
import com.example.lab5.model.Rate;
import com.example.lab5.repository.RateRepository;
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
public class RateService {

    private final RateRepository rateRepository;
    private final TeacherClassRepository teacherClassRepository;
    private final RateMapper rateMapper;
    private final RateCreateMapper rateCreateMapper;

    public List<RateDTO> findAll() {
        List<Rate> rates = rateRepository.findAll();
        return rates.stream().map(rateMapper::toDTO).collect(Collectors.toList());
    }

    public RateDTO findById(UUID id) {
        Rate foundRate = rateRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ItemNotFoundException.ItemType.RATE, id));
        return rateMapper.toDTO(foundRate);
    }

    public RateDTO save(RateCreateDTO rateCreateDTO) {
        UUID groupID = UUID.fromString(rateCreateDTO.getGroupID());
        if (!teacherClassRepository.existsById(groupID)) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, groupID);

        Rate rate = rateCreateMapper.toEntity(rateCreateDTO);
        return rateMapper.toDTO(rateRepository.save(rate));
    }

    public RateDTO update(UUID id, RateCreateDTO rateCreateDTO) {
        Optional<Rate> existingRate = rateRepository.findById(id);
        if (existingRate.isEmpty()) throw new ItemNotFoundException(ItemNotFoundException.ItemType.RATE, id);

        UUID groupID = UUID.fromString(rateCreateDTO.getGroupID());
        if (!teacherClassRepository.existsById(groupID)) throw new ItemNotFoundException(ItemNotFoundException.ItemType.TEACHER_CLASS, groupID);

        rateCreateMapper.updateRateFromDTO(rateCreateDTO, existingRate.get());
        return rateMapper.toDTO(rateRepository.save(existingRate.get()));
    }

    public void delete(UUID id) {
        if (!rateRepository.existsById(id)) throw new ItemNotFoundException(ItemNotFoundException.ItemType.RATE, id);
        rateRepository.deleteById(id);
    }

    public String generateCSV() {
        List<Rate> rates = rateRepository.findAll();

        StringWriter stringWriter = new StringWriter();
        try (CSVWriter writer = new CSVWriter(stringWriter)) {
            writer.writeNext(new String[]{"ID", "Rate", "GroupID", "Date", "Comment"});

            for (Rate rate : rates) {
                writer.writeNext(new String[]{
                        rate.getId().toString(),
                        Integer.toString(rate.getRate()),
                        rate.getGroup().getId().toString(),
                        rate.getDate().toString(),
                        rate.getComment()
                });
            }
        } catch (Exception e) {
            throw new SomethingWentWrongException();
        }

        return stringWriter.toString();
    }
}
