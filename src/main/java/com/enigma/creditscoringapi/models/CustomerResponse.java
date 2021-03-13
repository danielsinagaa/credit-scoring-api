package com.enigma.creditscoringapi.models;

import com.enigma.creditscoringapi.entity.enums.EmployeeType;
import com.enigma.creditscoringapi.entity.enums.NeedType;
import lombok.Data;

@Data
public class CustomerResponse {
    private String id;

    private String name;

    private EmployeeType employeeType;

    private NeedType needType;
}
