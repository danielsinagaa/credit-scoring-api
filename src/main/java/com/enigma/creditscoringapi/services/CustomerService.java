package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer, String> {
    @Autowired
    public CustomerService(@Qualifier("customerRepository") CustomerRepository repository) {
        super(repository);
    }
}
