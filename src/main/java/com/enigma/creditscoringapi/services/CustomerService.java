package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer, String> {
    @Autowired
    public CustomerService(@Qualifier("customerRepository") CustomerRepository repository) {
        super(repository);
    }

    @Qualifier("customerRepository")
    @Autowired
    CustomerRepository repository;

    public void softDelete(String id){
        repository.softDelete(id);
    }

    public Page<Customer> findAllByAdmin(String username, int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllBySubmitter(username, PageRequest.of(page, size, sort));
    }

    public Page<Customer> findAllContract(int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllContract(PageRequest.of(page, size, sort));
    }

    public Page<Customer> findAllNon(int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllNon(PageRequest.of(page, size, sort));
    }

    public Page<Customer> findAllRegular(int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllRegular(PageRequest.of(page, size, sort));
    }

    public Page<Customer> findAllContractBySubmitter(String username, int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllContractBySubmitter(username, PageRequest.of(page, size, sort));
    }

    public Page<Customer> findAllNonBySubmitter(String username, int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllNonBySubmitter(username, PageRequest.of(page, size, sort));
    }

    public Page<Customer> findAllRegularBySubmitter(String username, int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllRegularBySubmitter(username, PageRequest.of(page, size, sort));
    }
}
