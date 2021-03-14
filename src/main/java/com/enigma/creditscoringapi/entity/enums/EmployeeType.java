package com.enigma.creditscoringapi.entity.enums;

public enum EmployeeType {
    NON("NON"),
    REGULAR("REGULAR"),
    CONTRACT("CONTRACT");

    private String type;

    EmployeeType(String type) {
        this.type = type;
    }
}
