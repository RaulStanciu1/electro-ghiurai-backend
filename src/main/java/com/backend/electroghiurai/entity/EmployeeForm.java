package com.backend.electroghiurai.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeForm {
    private String firstName;
    private String lastName;
    private String email;
    private Date dateOfBirth;
    private String countryOfOrigin;
}
