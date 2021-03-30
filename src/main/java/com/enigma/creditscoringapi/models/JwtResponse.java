package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    private String roles;
    private String fullName;
    private String id;
    private Boolean inputCustomer;
    private Boolean readAllCustomer;
    private Boolean inputTransaction;
    private Boolean readAllTransaction;
    private Boolean approveTransaction;
    private Boolean readAllReport;
    private Boolean readAllReportByTransaction;
    private Boolean master;

    public JwtResponse(String token, String username, String email, String roles, String fullName, String id, Boolean inputCustomer, Boolean readAllCustomer, Boolean inputTransaction, Boolean readAllTransaction, Boolean approveTransaction, Boolean readAllReport, Boolean readAllReportByTransaction, Boolean master) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.fullName = fullName;
        this.id = id;
        this.inputCustomer = inputCustomer;
        this.readAllCustomer = readAllCustomer;
        this.inputTransaction = inputTransaction;
        this.readAllTransaction = readAllTransaction;
        this.approveTransaction = approveTransaction;
        this.readAllReport = readAllReport;
        this.readAllReportByTransaction = readAllReportByTransaction;
        this.master = master;
    }
}
