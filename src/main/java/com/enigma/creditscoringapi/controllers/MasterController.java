package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.ReportResponse;
import com.enigma.creditscoringapi.models.SignUpRequest;
import com.enigma.creditscoringapi.models.UserResponse;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.UsersRepository;
import com.enigma.creditscoringapi.services.RoleService;
import com.enigma.creditscoringapi.services.SendEmailService;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/master")
@RestController
public class MasterController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    UsersService service;

    @Autowired
    UsersRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleService roleService;

    @DeleteMapping("/{id}")
    public ResponseMessage deleteUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        users.setEmail(users.getEmail() + randomPassword());
        users.setUsername(users.getUsername() + randomPassword());
        service.save(users);

        UserResponse response = modelMapper.map(users, UserResponse.class);

        service.softDelete(id);

        return new ResponseMessage(200, "users : " + users.getUsername() , response);
    }

    @GetMapping
    public ResponseMessage findAll(PageSearch search) {

        Page<Users> users = service.findAll(new Users(), search.getPage(), search.getSize(), search.getSort());

        return getResponseMessage(users);
    }

    @PatchMapping("/{id}")
    public ResponseMessage editUser(@PathVariable String id, @RequestBody SignUpRequest request) throws MessagingException {
        Users entity = service.findById(id);

        modelMapper.map(request, entity);

        List<Role> roles = new ArrayList<>();

        roles.add(roleService.findRoleByName(request.getRole()));

        entity.setPassword(encoder.encode(request.getPassword()));

        entity.setRoles(roles);

        service.save(entity);

        sendEmailService.editUser(entity.getUsername(), request.getPassword(), entity.getRoles().get(0).getName(), entity.getEmail());

        return ResponseMessage.success(entity);
    }

    @GetMapping("/notverified")
    public ResponseMessage notverified(PageSearch search) {

        Page<Users> users = repository.findAllNotVerified(PageRequest.of(search.getPage(), search.getSize(), search.getSort()));

        return getResponseMessage(users);
    }

    @GetMapping("/verified")
    public ResponseMessage allVerified(PageSearch search) {

        Page<Users> users = repository.findAllVerified(PageRequest.of(search.getPage(), search.getSize(), search.getSort()));

        return getResponseMessage(users);
    }

    @GetMapping("/{id}")
    public ResponseMessage findUserById(@PathVariable String id) {
        Users users = service.findById(id);

        if (users == null) {
            throw new EntityNotFoundException();
        }

        UserResponse response = modelMapper.map(users, UserResponse.class);
        response.setRole(response.getRoles().get(0).getName());

        return ResponseMessage.success(response);
    }

    @PostMapping("/signup")
    public ResponseMessage registerUser(@Valid @RequestBody SignUpRequest request) throws MessagingException {

        if (repository.existsByUsername(request.getUsername())) {
            return new ResponseMessage(409, "not allowed", " username is already use");
        }

        if (repository.existsByEmail(request.getEmail())) {
            return new ResponseMessage(409, "not allowed", " email is already use");
        }

        String token = generateVerificationToken();

        String password = randomPassword();

        Users user = modelMapper.map(request, Users.class);
        user.setPassword(encoder.encode(password));
        user.setIsVerified(false);
        user.setVerifiedToken(token);
        user.setProfilePicture(request.getProfilePicture());

        if (request.getProfilePicture().isEmpty() || request.getProfilePicture().isBlank() || request.getProfilePicture() == null){
            user.setProfilePicture("https://res.cloudinary.com/nielnaga/image/upload/v1615870303/download-removebg-preview_zyrump.png");
        }

        List<Role> roles = new ArrayList<>();

        roles.add(roleService.findRoleByName(request.getRole()));

        user.setRoles(roles);
        repository.save(user);

        sendEmailService.sendEmailVerificationToken(user.getUsername(), password, token, user.getEmail());

        return ResponseMessage.success("User Registered successfully");
    }

    private String randomPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String saltStr = salt.toString().toLowerCase();
        return saltStr;
    }

    private String generateVerificationToken() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder stringBuilder = new StringBuilder();
        Random rnd = new Random();

        while (stringBuilder.length() <= 20) { // length of the random string.
            int index = (int) (rnd.nextFloat() * characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return stringBuilder.toString();
    }

    private ResponseMessage getResponseMessage(Page<Users> users) {
        List<Users> usersList = users.toList();

        List<UserResponse> responses = usersList.stream()
                .map(e -> modelMapper.map(e, UserResponse.class))
                .collect(Collectors.toList());

        for (UserResponse u : responses){
            u.setRole(u.getRoles().get(0).getName());
        }

        PagedList<ReportResponse> response = new PagedList(responses, users.getNumber(),
                users.getSize(), users.getTotalElements());

        return ResponseMessage.success(response);
    }

}
