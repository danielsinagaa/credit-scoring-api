package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.ReportExcelExporter;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public ResponseMessage findById(@PathVariable String id) {
        TransactionReport entity = service.findById(id);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        ReportResponse response = modelMapper.map(entity, ReportResponse.class);

        return ResponseMessage.success(response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search) {
        Page<TransactionReport> entityPage = service.findAll(new TransactionReport(), search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/type/non")
    public ResponseMessage findAllNon(PageSearch search){
        Page<TransactionReport> entityPage = service.findAllNon(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/principal")
    public ResponseMessage findAllBySubmitter(PageSearch search, Principal principal){
        Page<TransactionReport> entityPage = service.findAllBySubmitter(principal.getName(),search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/type/contract")
    public ResponseMessage findAllContract(PageSearch search){
        Page<TransactionReport> entityPage = service.findAllContract(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/approved")
    public ResponseMessage findAllRejected(PageSearch search){
        Page<TransactionReport> entityPage = service.findAllRejected(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/rejected")
    public ResponseMessage findAllApproved(PageSearch search){
        Page<TransactionReport> entityPage = service.findAllApproved(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/type/regular")
    public ResponseMessage findAllRegular(PageSearch search){
        Page<TransactionReport> entityPage = service.findAllRegular(search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(entityPage);
    }

    @GetMapping("/download")
    public void downloadReportExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDate = dateFormatter.format(new Date());
        String fileName = "Transaction_Report_" + currentDate + ".xlsx";

        String headerValue = "attachment; filename=" + fileName;

        response.setHeader(headerKey, headerValue);

        List<TransactionReport> reports = service.findAll();

        ReportExcelExporter excelExporter = new ReportExcelExporter(reports);
        excelExporter.export(response);
    }

    private ResponseMessage getResponseMessage(Page<TransactionReport> entityPage) {
        List<TransactionReport> entities = entityPage.toList();

        List<ReportResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, ReportResponse.class))
                .collect(Collectors.toList());

        PagedList<ReportResponse> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }
}
