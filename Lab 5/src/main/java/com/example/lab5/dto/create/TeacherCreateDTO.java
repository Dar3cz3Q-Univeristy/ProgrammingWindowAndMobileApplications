package com.example.lab5.dto.create;

import com.example.lab5.annotation.ValidEnum;
import com.example.lab5.model.TeacherCondition;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCreateDTO {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Surname cannot be null")
    private String surname;
    @NotNull(message = "Condition cannot be null")
    @ValidEnum(enumClass = TeacherCondition.class)
    private String condition;
    @NotNull(message = "Birthday cannot be null")
    @Past(message = "Birthday must be a past date")
    private Date birthday;
    @NotNull(message = "Salary cannot be null")
    @Min(value = 0, message = "Salary must be greater than or equal to 0")
    private double salary;
    @NotNull(message = "Group ID cannot be null")
    @UUID(message = "Group ID must be a valid UUID")
    private String groupID;
}
