package com.enigma.creditscoringapi.models;

import com.enigma.creditscoringapi.entity.enums.EmployeeType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequest {
    private String name;

    private EmployeeType employeeType;

    private Long idNumber;

    private String idPhoto;

    private String profilePhoto;

    private String email;

    private String address;

    private LocalDate contractStart;

    private Integer contractLength;
}
