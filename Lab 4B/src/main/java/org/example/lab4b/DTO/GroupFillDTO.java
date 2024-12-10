package org.example.lab4b.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GroupFillDTO {

    private String name;
    private int capacity;
    private double fillPercentage;
}
