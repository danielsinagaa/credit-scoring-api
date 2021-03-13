package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Approval;
import com.enigma.creditscoringapi.repository.ApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalService extends AbstractService<Approval, String> {

    @Autowired
    public ApprovalService(ApprovalRepository repository) {
        super(repository);
    }
}
