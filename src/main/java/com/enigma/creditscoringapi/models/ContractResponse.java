package com.enigma.creditscoringapi.models;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractResponse extends CustomerResponse{

    private LocalDate contractStart;

    private Integer contractLength;

    private LocalDate contractEnd;
}
