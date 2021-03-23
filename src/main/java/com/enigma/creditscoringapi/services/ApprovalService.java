package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Approval;
import com.enigma.creditscoringapi.repository.ApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService extends AbstractService<Approval, String> {

    @Autowired
    ApprovalRepository repository;

    @Autowired
    public ApprovalService(ApprovalRepository repository) {
        super(repository);
    }

    public Page<Approval> findAllByAdmin(String username, int page, int size, Sort.Direction direction){
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "name") : Sort.by("name");
        return repository.findAllByStaff(username, PageRequest.of(page, size, sort));
    }
}
