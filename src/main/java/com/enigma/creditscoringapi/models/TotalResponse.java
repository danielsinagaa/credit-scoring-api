package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class TotalResponse {
    private Integer totalTransaction;
    private Integer totalApproved;
    private Integer totalRejected;
    private Integer totalCustomer;
    private Integer totalUser;
}
