package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    Page<Customer> findAllContract(Pageable pageable);

    Page<Customer> findAllNon(Pageable pageable);

    Page<Customer> findAllRegular(Pageable pageable);
}
