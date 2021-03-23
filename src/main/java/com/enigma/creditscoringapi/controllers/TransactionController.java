package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Approval;
import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.entity.NeedType;
import com.enigma.creditscoringapi.entity.Transaction;
import com.enigma.creditscoringapi.models.ContractResponse;
import com.enigma.creditscoringapi.models.TransactionRequest;
import com.enigma.creditscoringapi.models.TransactionResponse;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.ApprovalService;
import com.enigma.creditscoringapi.services.CustomerService;
import com.enigma.creditscoringapi.services.NeedTypeService;
import com.enigma.creditscoringapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DecimalFormat;
import java.time.LocalDate;

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

    @Autowired
    private NeedTypeService needTypeService;

    @Autowired
    private ApprovalService approvalService;

    private final DecimalFormat df = new DecimalFormat("#.##");

    @PostMapping
    public ResponseMessage add(@RequestBody TransactionRequest request, Principal principal) {
        Transaction entity = modelMapper.map(request, Transaction.class);

        NeedType needType = needTypeService.findById(request.getNeedType());
        entity.setNeedType(needType);

        Customer customer = customerService.findById(request.getCustomer());
        entity.setCustomer(customer);

        Double income = request.getIncome().doubleValue();

        Double loan = request.getLoan().doubleValue();
        Double mainLoan = request.getLoan().doubleValue() / request.getTenor();

        entity.setMainLoan(Double.valueOf(df.format(mainLoan)));

        Double interestRate = Double.valueOf(request.getInterestRate());
        Double interest = (loan * interestRate) / 100;

        entity.setInterest(Double.valueOf(df.format(interest)));

        Double installment = mainLoan + interest;

        entity.setInstallment(Double.valueOf(df.format(installment)));

        Double installmentTotal = installment * request.getTenor();

        entity.setInstallmentTotal(Double.valueOf(df.format(installmentTotal)));

        Double creditRatio = ((installment + request.getOutcome()) / income) * 100;

        entity.setCreditRatio(Double.valueOf(df.format(creditRatio)));

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

        entity.setSubmitter(principal.getName());
        service.save(entity);

        TransactionResponse data = modelMapper.map(entity, TransactionResponse.class);

        Approval approval = new Approval(entity, null, principal.getName());

        approvalService.save(approval);

        return ResponseMessage.success(data);
    }
}
