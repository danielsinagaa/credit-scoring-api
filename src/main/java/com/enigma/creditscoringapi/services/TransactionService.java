package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Transaction;
import com.enigma.creditscoringapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends AbstractService<Transaction, String> {
    @Autowired
    public TransactionService(TransactionRepository repository) {
        super(repository);
    }
}
