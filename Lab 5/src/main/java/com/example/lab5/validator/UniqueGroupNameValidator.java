package com.example.lab5.validator;

import com.example.lab5.annotation.UniqueGroupName;
import com.example.lab5.repository.TeacherClassRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueGroupNameValidator implements ConstraintValidator<UniqueGroupName, String> {

    private final TeacherClassRepository teacherClassRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !teacherClassRepository.existsByName(value);
    }
}
