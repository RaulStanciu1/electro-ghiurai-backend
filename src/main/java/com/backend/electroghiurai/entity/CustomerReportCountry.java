package com.backend.electroghiurai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CustomerReportCountry {
    private String first_country;
    private int first_country_number;
    private String second_country;
    private int second_country_number;
    private String third_country;
    private Integer third_country_number;
}
