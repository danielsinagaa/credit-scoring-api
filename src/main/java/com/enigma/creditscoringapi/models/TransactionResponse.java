package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class TransactionResponse {
    private String id;

    private CustomerResponse customer;

    private Integer income;

    private Integer outcome;

    private Integer loan;

    private Integer tenor;

    private Integer interestRate;

    private Double mainLoan;

    private Double interest;

    private Double installment;
}
