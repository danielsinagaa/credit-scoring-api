package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class ApprovalRequest {
    private Boolean approve;

    private String transaction;
}
