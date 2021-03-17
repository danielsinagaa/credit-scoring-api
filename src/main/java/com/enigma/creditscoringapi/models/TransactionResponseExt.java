package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class TransactionResponseExt extends TransactionResponse{
    private Double creditRatio;

    private Boolean financeCriteria;

    private Boolean employeeCriteria;
}
