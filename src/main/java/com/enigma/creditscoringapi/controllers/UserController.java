package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.models.EditUsers;
import com.enigma.creditscoringapi.models.UserResponse;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.UsersRepository;
import com.enigma.creditscoringapi.services.RoleService;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UsersService service;

    @Autowired
    UsersRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleService roleService;

    @PatchMapping
    public ResponseMessage editUser(@RequestBody EditUsers edit, Principal principal) {
        Users user = service.findByUsername(principal.getName());

        if (!user.getUsername().equals(principal.getName()) ) {
            return new ResponseMessage(400, "Bad Request", "id not match with current user");
        }

        user = modelMapper.map(edit, Users.class);
        service.save(user);

        UserResponse response = modelMapper.map(user, UserResponse.class);

        return ResponseMessage.success(response);
    }

    @PatchMapping("/password")
    public ResponseMessage changePassword(@RequestBody EditUsers edit, Principal principal) {
        Users user = service.findByUsername(principal.getName());

        if (!user.getUsername().equals(principal.getName())) {
            return new ResponseMessage(400, "Bad Request", "id not match with current user");
        }

        System.out.println(encoder.matches(edit.getOldPassword(), user.getPassword()));

        if (!encoder.matches(edit.getOldPassword(), user.getPassword())) {
            return new ResponseMessage(400, "wrong password", false);
        }

        user.setPassword(encoder.encode(edit.getPassword()));
        service.save(user);

        return new ResponseMessage(200, "password changed!", true);
    }

    @GetMapping("/username/{username}")
    public ResponseMessage existUsername(@PathVariable String username, Principal principal) {
        Users user = service.findByUsername(principal.getName());

        if (!user.getUsername().equals(username) && service.existByUsername(username)) {
            return new ResponseMessage(400, "username exist", false);
        }

        return ResponseMessage.success(true);
    }

    @GetMapping("/email/{email}")
    public ResponseMessage existEmail(@PathVariable String email, Principal principal) {
        Users user = service.findByUsername(principal.getName());

        if (!user.getEmail().equals(email) && service.existByEmail(email)) {
            return new ResponseMessage(400, "email exist", false);
        }

        return ResponseMessage.success(true);
    }
}
