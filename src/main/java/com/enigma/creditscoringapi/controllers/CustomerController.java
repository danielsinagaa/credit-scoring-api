package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.ContractEmployee;
import com.enigma.creditscoringapi.entity.Customer;
import com.enigma.creditscoringapi.entity.NonEmployee;
import com.enigma.creditscoringapi.entity.RegularEmployee;
import com.enigma.creditscoringapi.entity.enums.EmployeeType;
import com.enigma.creditscoringapi.exceptions.BadRequestException;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.ContractResponse;
import com.enigma.creditscoringapi.models.CustomerRequest;
import com.enigma.creditscoringapi.models.CustomerResponse;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/customer")
@RestController
public class CustomerController {
    @Autowired
    private CustomerService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseMessage add(@RequestBody CustomerRequest request){

        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Customer entity;
        CustomerResponse response;

        switch (request.getEmployeeType()){
            case REGULAR:
                entity = modelMapper.map(request, RegularEmployee.class);
                response = modelMapper.map(entity, CustomerResponse.class);
                break;
            case CONTRACT:
                ContractEmployee contract = modelMapper.map(request, ContractEmployee.class);
                LocalDate date = LocalDate.parse(request.getContractStart(), formatters);
                contract.setContractStart(date);
                contract.setContractEnd(date.plusMonths(request.getContractLength()));
                entity = contract;
                response = modelMapper.map(entity, ContractResponse.class);
                break;
            case NON:
                entity = modelMapper.map(request, NonEmployee.class);
                response = modelMapper.map(entity, CustomerResponse.class);
                break;
            default:
                throw new BadRequestException();
        }

        service.save(entity);

        response.setId(entity.getId());

        return ResponseMessage.success(response);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage deleteById(@PathVariable String id){
        Customer entity = service.removeById(id);

        if (entity == null) {
            throw new EntityNotFoundException();
        }

        CustomerResponse response = modelMapper.map(entity, CustomerResponse.class);
        return ResponseMessage.success(response);
    }

    @GetMapping("/{id}")
    public ResponseMessage findById(@PathVariable String id){
        Customer entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }

        CustomerResponse response;

        switch (entity.getEmployeeType()){
            case CONTRACT:
                response = modelMapper.map(entity, ContractResponse.class);
                break;
            case REGULAR:
            case NON:
                response = modelMapper.map(entity, CustomerResponse.class);
                break;
            default:
                throw new BadRequestException();
        }

        return ResponseMessage.success(response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search){
        Page<Customer> entityPage = service.findAll(new Customer(), search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/non")
    public ResponseMessage non(PageSearch search){
        Page<Customer> entityPage = service.findAllNon(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/contract")
    public ResponseMessage findAlltype(@Valid PageSearch request) {
        Page<Customer> entityPage = service.findAllContract(request.getPage(),
                request.getSize(), request.getSort());
        List<Customer> entities = entityPage.toList();

        List<ContractResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, ContractResponse.class))
                .collect(Collectors.toList());

        PagedList<ContractResponse> data = new PagedList(models,
                entityPage.getNumber(), entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }


    @GetMapping("/regular")
    public ResponseMessage regular(PageSearch search){
        Page<Customer> entityPage = service.findAllRegular(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    private ResponseMessage getResponseMessage(Page<Customer> entityPage) {
        List<Customer> entities = entityPage.toList();

        List<CustomerResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, CustomerResponse.class))
                .collect(Collectors.toList());

        PagedList<CustomerResponse> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }
}
