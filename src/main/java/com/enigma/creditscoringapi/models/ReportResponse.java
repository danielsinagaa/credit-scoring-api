package com.enigma.creditscoringapi.models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReportResponse {
    private String id;

    private ApprovalResponseExt approval;

    private LocalDate submitDate;

    private LocalDate approvalDate;
}
