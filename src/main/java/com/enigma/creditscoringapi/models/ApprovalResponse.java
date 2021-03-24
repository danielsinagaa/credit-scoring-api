package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class ApprovalResponse {
    private String id;

    private Boolean approve;

    private TransactionResponse transaction;
}
