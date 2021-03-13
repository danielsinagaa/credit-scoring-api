package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RegularEmployeeService  extends AbstractService<Customer, String> {
    @Autowired
    public RegularEmployeeService(@Qualifier("regularEmployeeRepository") CustomerRepository repository) {
        super(repository);
    }
}