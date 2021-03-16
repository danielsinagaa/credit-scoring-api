package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.entity.Transaction;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.ContractResponse;
import com.enigma.creditscoringapi.models.TransactionRequest;
import com.enigma.creditscoringapi.models.TransactionResponseExt;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.models.TransactionResponse;
import com.enigma.creditscoringapi.services.CustomerService;
import com.enigma.creditscoringapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/transaction")
@RestController
public class TransactionController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionService service;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseMessage add(@RequestBody TransactionRequest request) {
        Transaction entity = modelMapper.map(request, Transaction.class);

        Customer customer = customerService.findById(request.getCustomer());
        Double income = request.getIncome().doubleValue();

        entity.setCustomer(customer);

        switch (customer.getEmployeeType()) {
            case CONTRACT:
            case NON:
                income /= 2;
                break;
            default:
                income = request.getIncome().doubleValue();
        }

        Double loan = request.getLoan().doubleValue();

        Double mainLoan = request.getLoan().doubleValue() / request.getTenor();

        entity.setMainLoan(mainLoan);

        Double interestRate = Double.valueOf(request.getInterestRate());

        Double interest = (loan * interestRate) / 100;

        entity.setInterest(interest);

        Double installment = mainLoan + interest;

        entity.setInstallment(installment);

        Double installmentTotal = installment * request.getTenor();

        entity.setInstallmentTotal(installmentTotal);

        Double creditRatio = ((installment + request.getOutcome()) / income) * 100;

        entity.setCreditRatio(creditRatio);

        Boolean financeCriteria = creditRatio < 30.0;

        entity.setFinanceCriteria(financeCriteria);

        switch (customer.getEmployeeType()) {
            case CONTRACT:
                ContractResponse response = modelMapper.map(customer, ContractResponse.class);
                LocalDate date = LocalDate.now().plusMonths(request.getTenor());
                Boolean employeeCriteria = date.isBefore(response.getContractEnd());
                entity.setEmployeeCriteria(employeeCriteria);
                break;
            case REGULAR:
                entity.setEmployeeCriteria(true);
                break;
            default:
                entity.setEmployeeCriteria(null);
        }

        service.save(entity);

        TransactionResponse data = modelMapper.map(entity, TransactionResponse.class);

        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage findById(@PathVariable String id) {
        Transaction entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        TransactionResponseExt response = modelMapper.map(entity, TransactionResponseExt.class);

        return ResponseMessage.success(response);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage deleteById(@PathVariable String id) {
        Transaction entity = service.findById(id);

        if (entity == null) {
            throw new EntityNotFoundException();
        }

        service.removeById(id);

        TransactionResponse response = modelMapper.map(entity, TransactionResponse.class);

        return ResponseMessage.success(response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search) {
        Page<Transaction> entityPage = service.findAll(new Transaction(), search.getPage(), search.getSize(), search.getSort());

        List<Transaction> entities = entityPage.toList();

        List<TransactionResponseExt> responses = entities.stream()
                .map(e -> modelMapper.map(e, TransactionResponseExt.class))
                .collect(Collectors.toList());

        PagedList<TransactionResponseExt> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }
}
