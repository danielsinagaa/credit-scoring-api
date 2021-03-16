package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.TransactionReport;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.ReportResponse;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.ReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/report")
@RestController
public class ReportController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ReportService service;

    @GetMapping("/{id}")
    public ResponseMessage findById(@PathVariable String id){
        TransactionReport entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ReportResponse response = modelMapper.map(entity, ReportResponse.class);

        return ResponseMessage.success(response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search){
        Page<TransactionReport> entityPage = service.findAll(new TransactionReport(), search.getPage(), search.getSize(), search.getSort());

        List<TransactionReport> entities = entityPage.toList();

        List<ReportResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, ReportResponse.class))
                .collect(Collectors.toList());

        PagedList<ReportResponse> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }
}
