package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
