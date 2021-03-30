package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.models.TotalResponse;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.ApprovalRepository;
import com.enigma.creditscoringapi.services.CustomerService;
import com.enigma.creditscoringapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/total")
@RestController
public class TotalController {
    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UsersService userService;

    @GetMapping
    public ResponseMessage total(){
        TotalResponse totalResponse = new TotalResponse();

        totalResponse.setTotalTransaction(approvalRepository.findAllApproval().size());
        totalResponse.setTotalApproved(approvalRepository.findAllApproved().size());
        totalResponse.setTotalRejected(approvalRepository.findAllRejected().size());
        totalResponse.setTotalCustomer(customerService.findAll().size());
        totalResponse.setTotalUser(userService.findAll().size());

        return ResponseMessage.success(totalResponse);
    }
}
