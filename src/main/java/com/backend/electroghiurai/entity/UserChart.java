package com.backend.electroghiurai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChart {
    private Long customers;
    private Long juniorDevelopers;
    private Long seniorDevelopers;
}
