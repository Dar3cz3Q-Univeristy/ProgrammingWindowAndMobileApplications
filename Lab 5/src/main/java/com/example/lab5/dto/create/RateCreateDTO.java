package com.example.lab5.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateCreateDTO {
    @NotNull
    @Min(value = 1, message = "Rate must be grater than or equal 1")
    @Max(value = 6, message = "Rate must be smaller than or equal 6")
    int rate;
    @NotNull
    @UUID(message = "Group ID must be a valid UUID")
    String groupID;
    String comment;
}
