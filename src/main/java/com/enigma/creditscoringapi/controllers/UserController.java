package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UsersService service;

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

    @GetMapping("/activate/{id}")
    public ResponseMessage ActiveUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        users.setActive(true);

        return ResponseMessage.success(users);
    }
}
