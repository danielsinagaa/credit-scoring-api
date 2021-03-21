package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Transaction;
import com.enigma.creditscoringapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends AbstractService<Transaction, String> {

    @Autowired
    TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        super(repository);
    }

    public Page<Transaction> findAllContract(int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "name") : Sort.by("name");
        return repository.findAllContract(PageRequest.of(page, size, sort));
    }

    public Page<Transaction> findAllNon(int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "name") : Sort.by("name");
        return repository.findAllNon(PageRequest.of(page, size, sort));
    }

    public Page<Transaction> findAllRegular(int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "name") : Sort.by("name");
        return repository.findAllRegular(PageRequest.of(page, size, sort));
    }
}
