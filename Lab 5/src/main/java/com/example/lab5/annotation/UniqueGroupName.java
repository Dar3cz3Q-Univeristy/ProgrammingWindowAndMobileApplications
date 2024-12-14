package com.example.lab5.annotation;

import com.example.lab5.validator.UniqueGroupNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueGroupNameValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueGroupName {

    String message() default "Class with this name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
