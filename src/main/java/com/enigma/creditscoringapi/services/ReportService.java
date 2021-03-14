package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.TransactionReport;
import com.enigma.creditscoringapi.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends AbstractService<TransactionReport, String> {

    @Autowired
    public ReportService(ReportRepository repository) {
        super(repository);
    }
}
