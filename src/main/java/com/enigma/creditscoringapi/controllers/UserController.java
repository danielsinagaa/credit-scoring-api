package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.EditUsers;
import com.enigma.creditscoringapi.models.ReportResponse;
import com.enigma.creditscoringapi.models.UserResponse;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.SendEmailService;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UsersService service;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private SendEmailService sendEmailService;

    @DeleteMapping("/{id}")
    public ResponseMessage deleteUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        users.setEmail(users.getEmail()+"DELETED");
        users.setUsername(users.getUsername()+"DELETED");
        service.save(users);

        UserResponse response = modelMapper.map(users, UserResponse.class);

        service.softDelete(id);

        return new ResponseMessage(200, "users : " + users.getUsername() + " deleted", response);
    }

    @PatchMapping("/{id}")
    public ResponseMessage editUser(@PathVariable String id, @RequestBody EditUsers edit, Principal principal) {
        Users user = service.findById(id);

        if (!user.getUsername().equals(principal.getName())) {
            return new ResponseMessage(400, "Bad Request", "id not match with current user");
        }

        user = modelMapper.map(edit, Users.class);
        service.save(user);

        UserResponse response = modelMapper.map(user, UserResponse.class);

        return ResponseMessage.success(response);
    }

    @PatchMapping("/password/{id}")
    public ResponseMessage changePassword(@PathVariable String id, @RequestBody EditUsers edit, Principal principal) {
        Users user = service.findById(id);

        if (!user.getUsername().equals(principal.getName())) {
            return new ResponseMessage(400, "Bad Request", "id not match with current user");
        }

        if (!encoder.matches(user.getPassword(), edit.getOldPassword())) {
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

    @GetMapping
    public ResponseMessage findAll(PageSearch search) {

        Page<Users> users = service.findAll(new Users(), search.getPage(), search.getSize(), search.getSort());

        List<Users> usersList = users.toList();

        List<UserResponse> responses = usersList.stream()
                .map(e -> modelMapper.map(e, UserResponse.class))
                .collect(Collectors.toList());

        for (UserResponse u : responses){
            u.setRole(u.getRoles().get(0).getName().name());
        }

        PagedList<ReportResponse> response = new PagedList(responses, users.getNumber(),
                users.getSize(), users.getTotalElements());

        return ResponseMessage.success(response);
    }

    @GetMapping("/{id}")
    public ResponseMessage findUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        UserResponse response = modelMapper.map(users, UserResponse.class);
        response.setRole(response.getRoles().get(0).getName().name());

        return ResponseMessage.success(response);
    }

    @GetMapping("/activate/{id}")
    public ResponseMessage ActiveUserById(@PathVariable String id) throws MessagingException {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        if (users.getActive()) return new ResponseMessage(400, "Account has been already active", users);

        users.setActive(true);
        service.save(users);

        sendEmailService.sendEmailVerificationToken(users.getVerifiedToken(), users.getEmail());

        UserResponse response = modelMapper.map(users, UserResponse.class);

        return ResponseMessage.success(response);
    }
}
