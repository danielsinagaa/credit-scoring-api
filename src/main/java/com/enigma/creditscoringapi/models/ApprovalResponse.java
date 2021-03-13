package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class ApprovalResponse {
    private Boolean approve;

    private TransactionResponse transaction;
}
