package com.enigma.creditscoringapi.models;

import com.enigma.creditscoringapi.entity.enums.EmployeeType;
import com.enigma.creditscoringapi.entity.enums.NeedType;
import lombok.Data;

@Data
public class CustomerResponse {
    private String id;

    private String name;

    private Long idNumber;

    private String idPhoto;

    private String submitter;

    private String profilePhoto;

    private String email;

    private String address;

    private EmployeeType employeeType;
}
