package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class RoleRequest {
    private String name;

    private Boolean inputCustomer;

    private Boolean readAllCustomer;

    private Boolean inputTransaction;

    private Boolean readAllTransaction;

    private Boolean approveTransaction;

    private Boolean readAllReport;

    private Boolean readAllReportByTransaction;
}
