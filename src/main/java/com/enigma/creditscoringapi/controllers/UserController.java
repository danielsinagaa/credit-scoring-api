package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.pages.Active;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.SendEmailService;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UsersService service;

    @Autowired
    private SendEmailService sendEmailService;

    @DeleteMapping("/{id}")
    public ResponseMessage deleteUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        if (!users.getIsVerified() || !users.getActive()) {
            return new ResponseMessage(400, "Account hasnt been actived yet", null);
        }

        users.setActive(false);

        return ResponseMessage.success(users);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search) {

        Page<Users> users = service.findAll(new Users(), search.getPage(), search.getSize(), search.getSort());

        return ResponseMessage.success(users);
    }

    @GetMapping("/{id}")
    public ResponseMessage findUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        return ResponseMessage.success(users);
    }

    @PostMapping("/activate")
    public ResponseMessage ActiveUserById(@RequestBody Active id) throws MessagingException {
        Users users = service.findById(id.getId());

        if (users == null) {
            throw new EntityNotFoundException();
        }

        if (users.getActive()) return new ResponseMessage(400, "Account has been already active", users);

        users.setActive(true);
        service.save(users);

        sendEmailService.sendEmailVerificationToken(users.getVerifiedToken(), users.getEmail());

        return ResponseMessage.success(users);
    }
}
