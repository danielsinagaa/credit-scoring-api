package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Approval;
import com.enigma.creditscoringapi.entity.Transaction;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.ApprovalRequest;
import com.enigma.creditscoringapi.models.ApprovalResponse;
import com.enigma.creditscoringapi.models.TransactionResponse;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.ApprovalService;
import com.enigma.creditscoringapi.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/approval")
@RestController
public class ApprovalController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ApprovalService service;

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseMessage add(@RequestBody ApprovalRequest request){
        Transaction transaction = transactionService.findById(request.getTransaction());

        Approval entity = new Approval(transaction, request.getApprove());

        service.save(entity);

        ApprovalResponse response = modelMapper.map(entity, ApprovalResponse.class);

        return ResponseMessage.success(response);
    }
    @GetMapping("/{id}")
    public ResponseMessage findById(@PathVariable String id){
        Approval entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ApprovalResponse response = modelMapper.map(entity, ApprovalResponse.class);

        return ResponseMessage.success(response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search){
        Page<Approval> entityPage = service.findAll(new Approval(), search.getPage(), search.getSize(), search.getSort());

        List<Approval> entities = entityPage.toList();

        List<ApprovalResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, ApprovalResponse.class))
                .collect(Collectors.toList());

        PagedList<ApprovalResponse> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }
}
