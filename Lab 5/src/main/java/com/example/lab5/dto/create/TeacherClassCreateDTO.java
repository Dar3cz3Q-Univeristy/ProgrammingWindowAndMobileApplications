package com.example.lab5.dto.create;

import com.example.lab5.annotation.UniqueGroupName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherClassCreateDTO {
    @NotNull(message = "Class name cannot be null")
    @UniqueGroupName
    private String name;
    @NotNull(message = "Capacity cannot be null")
    @Min(value = 0, message = "Capacity must be greater than or equal to 0")
    private int capacity;
}
