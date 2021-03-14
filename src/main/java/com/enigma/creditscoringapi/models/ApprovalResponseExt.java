package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class ApprovalResponseExt{
    private Boolean approve;

    private TransactionResponseExt transaction;

}
