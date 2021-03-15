package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class TransactionRequest {
    private String customer;

    private String notes;

    private Integer income;

    private Integer outcome;

    private Integer loan;

    private Integer tenor;

    private Integer interestRate;
}
