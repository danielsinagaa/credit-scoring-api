package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Page<Transaction> findAllContract(Pageable pageable);

    Page<Transaction> findAllNon(Pageable pageable);

    Page<Transaction> findAllRegular(Pageable pageable);
}
