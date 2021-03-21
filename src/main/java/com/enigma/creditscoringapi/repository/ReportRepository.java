package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.TransactionReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<TransactionReport, String> {

    Page<TransactionReport> findAllContract(Pageable pageable);

    Page<TransactionReport> findAllNon(Pageable pageable);

    Page<TransactionReport> findAllRegular(Pageable pageable);

    Page<TransactionReport> findAllBySubmitter(String submitter, Pageable pageable);
}
