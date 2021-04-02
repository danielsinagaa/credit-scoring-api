package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.TransactionReport;
import com.enigma.creditscoringapi.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReportService extends AbstractService<TransactionReport, String> {

    @Autowired
    ReportRepository repository;

    @Autowired
    public ReportService(ReportRepository repository) {
        super(repository);
    }

    public Page<TransactionReport> findAllContract(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllContract(PageRequest.of(page, size, sort));
    }

    public Page<TransactionReport> findAllNon(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllNon(PageRequest.of(page, size, sort));
    }

    public Page<TransactionReport> findAllRejected(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllRejected(PageRequest.of(page, size, sort));
    }

    public Page<TransactionReport> findAllApproved(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllApproved(PageRequest.of(page, size, sort));
    }

    public Page<TransactionReport> findAllRegular(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllRegular(PageRequest.of(page, size, sort));
    }

    public Page<TransactionReport> findAllBySubmitter(String submitter, int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return repository.findAllBySubmitter(submitter, PageRequest.of(page, size, sort));
    }
}
