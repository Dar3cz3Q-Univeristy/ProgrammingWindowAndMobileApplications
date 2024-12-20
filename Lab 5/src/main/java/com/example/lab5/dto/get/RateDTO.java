package com.example.lab5.dto.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateDTO {
    String id;
    int rate;
    String groupID;
    Date date;
    String comment;
}
