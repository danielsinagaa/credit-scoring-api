package com.enigma.creditscoringapi.models;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TransactionResponseExt extends TransactionResponse{
    private Double creditRatio;

    private Boolean financeCriteria;

    private Boolean employeeCriteria;
}
