package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.TransactionReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<TransactionReport, String> {
}
