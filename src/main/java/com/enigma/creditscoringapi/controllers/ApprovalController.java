package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Approval;
import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.entity.TransactionReport;
import com.enigma.creditscoringapi.entity.enums.EmployeeType;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.*;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.ApprovalService;
import com.enigma.creditscoringapi.services.CustomerService;
import com.enigma.creditscoringapi.services.ReportService;
import com.enigma.creditscoringapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/approval")
@RestController
public class ApprovalController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApprovalService service;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ReportService reportService;

    @Autowired
    CustomerService customerService;

    @PatchMapping("/{id}")
    public ResponseMessage approve(@PathVariable String id, @RequestBody ApprovalRequest request){
        Approval entity = service.findById(id);
        System.out.println(entity);

        entity.setApprove(request.getApprove());
        service.save(entity);

        TransactionReport report = new TransactionReport();
        report.setApproval(entity);

        LocalDate submitDate = LocalDate.from(entity.getTransaction().getCreatedDate());

        report.setSubmitDate(submitDate);
        report.setApprovalDate(LocalDate.now());

        reportService.save(report);

        ApprovalResponse response = modelMapper.map(entity, ApprovalResponse.class);

        return ResponseMessage.success(response);
    }

    @GetMapping("/principal/{id}")
    public ResponseMessage findByIdStaff(@PathVariable String id) {
        Approval entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ApprovalResponse response = modelMapper.map(entity, ApprovalResponse.class);

        Customer customer = entity.getTransaction().getCustomer();

        if (customer.getEmployeeType() == EmployeeType.CONTRACT) {
            CustomerResponse customerResponse = modelMapper.map(customer, ContractResponse.class);
            response.getTransaction().setCustomer(customerResponse);
        }

        return ResponseMessage.success(response);
    }

    @GetMapping("/{id}")
    public ResponseMessage findById(@PathVariable String id) {
        Approval entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ApprovalResponseExt response = modelMapper.map(entity, ApprovalResponseExt.class);

        Customer customer = entity.getTransaction().getCustomer();

        if (customer.getEmployeeType() == EmployeeType.CONTRACT) {
            CustomerResponse customerResponse = modelMapper.map(customer, ContractResponse.class);
            response.getTransaction().setCustomer(customerResponse);
        }

        return ResponseMessage.success(response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search) {
        Page<Approval> entityPage = service.findAll(new Approval(), search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/waiting")
    public ResponseMessage findAllNull(PageSearch search) {
        Page<Approval> entityPage = service.findAllNull(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/approved")
    public ResponseMessage findAllApproved(PageSearch search) {
        Page<Approval> entityPage = service.findAllApproved(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/rejected")
    public ResponseMessage findAllRejected(PageSearch search) {
        Page<Approval> entityPage = service.findAllRejected(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/principal")
    public ResponseMessage findAllByStaff(PageSearch search, Principal principal) {
        Page<Approval> entityPage = service.findAllByAdmin(principal.getName(), search.getPage(), search.getSize(), search.getSort());

        List<Approval> entities = entityPage.toList();

        List<ApprovalResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, ApprovalResponse.class))
                .collect(Collectors.toList());

        PagedList<ApprovalResponse> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }

    private ResponseMessage getResponseMessage(Page<Approval> entityPage) {
        List<Approval> entities = entityPage.toList();

        List<ApprovalResponseExt> responses = entities.stream()
                .map(e -> modelMapper.map(e, ApprovalResponseExt.class))
                .collect(Collectors.toList());

        PagedList<ApprovalResponseExt> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }
}
