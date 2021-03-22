package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.entity.enums.ERole;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.JwtResponse;
import com.enigma.creditscoringapi.models.LoginRequest;
import com.enigma.creditscoringapi.models.SignUpRequest;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.UsersRepository;
import com.enigma.creditscoringapi.security.jwt.JwtUtils;
import com.enigma.creditscoringapi.security.service.UserDetailsImpl;
import com.enigma.creditscoringapi.services.RoleService;
import com.enigma.creditscoringapi.services.SendEmailService;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepository repository;

    @Autowired
    RoleService service;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private SendEmailService sendEmailService;

    @PostMapping("/login")
    public ResponseMessage authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Users user = repository.getByUsername(loginRequest.getUsername());
        if (user == null) {
            user = repository.getByEmail(loginRequest.getUsername());
            if (user == null) {
                return new ResponseMessage(403, "no username or email found", null);
            }
        }

        if (!user.getActive() || !user.getIsVerified()) {
            return new ResponseMessage(403, "account has not been verified yet", null);
        }

        if (encoder.matches(user.getPassword(), loginRequest.getPassword())) {
            return new ResponseMessage(403, "your password is wrong", null);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse response = new JwtResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles.get(0),
                user.getFullName());

        return ResponseMessage.success(response);
    }

    @PostMapping("/forgot")
    public ResponseMessage forgotPassword(@Valid @RequestBody SignUpRequest request) throws MessagingException {
        Users users = repository.getByEmail(request.getEmail());

        if (users == null){
            throw new EntityNotFoundException();
        }

        String newPassword = randomPassword();

        users.setPassword(encoder.encode(newPassword));

        usersService.save(users);

        sendEmailService.forgotPassword(users.getUsername(), newPassword, users.getEmail());

        return new ResponseMessage(200, "success", "verification email has been sent");
    }

    @GetMapping("/verification/{token}")
    public ResponseMessage verification(@PathVariable String token) {
        Users user = usersService.findByToken(token);

        if (user == null) {
            return new ResponseMessage(400, "Verification token is not valid.", null);
        } else {
            user.setIsVerified(true);
            usersService.save(user);
            return new ResponseMessage(200, "Verification token is success.", null);
        }
    }

    @PostMapping("/signup")
    public ResponseMessage registerUser(@Valid @RequestBody SignUpRequest request) {

        if (repository.existsByUsername(request.getUsername())) {

            return new ResponseMessage(409, "not allowed", " username is already use");
        }

        if (repository.existsByEmail(request.getEmail())) {
            return new ResponseMessage(409, "not allowed", " email is already use");
        }

        String token = generateVerificationToken();

        Users user = modelMapper.map(request, Users.class);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setIsVerified(false);
        user.setVerifiedToken(token);

        if (request.getProfilePicture().isEmpty() || request.getProfilePicture().isBlank() || request.getProfilePicture() == null){
            user.setProfilePicture("https://res.cloudinary.com/nielnaga/image/upload/v1615870303/download-removebg-preview_zyrump.png");
        }

        String strRoles = request.getRole();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            Role staff = service.findRoleByName(ERole.STAFF);
            roles.add(staff);
        } else if (strRoles.contains("MASTER")) {
            Role master = service.findRoleByName(ERole.MASTER);
            roles.add(master);
        } else {
            Role supervisor = service.findRoleByName(ERole.SUPERVISOR);
            roles.add(supervisor);
        }

        user.setRoles(roles);
        repository.save(user);

        return ResponseMessage.success("User Registered successfully");
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

    @GetMapping("/username/{username}")
    public ResponseMessage existUsername(@PathVariable String username){

        if (usersService.existByUsername(username)){
            return new ResponseMessage(400, "username exist", false);
        }

        return ResponseMessage.success(true);
    }

    @GetMapping("/email/{email}")
    public ResponseMessage existEmail(@PathVariable String email){

        if (usersService.existByEmail(email)){
            return new ResponseMessage(400, "email exist", false);
        }

        return ResponseMessage.success(true);
    }

    private String randomPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String saltStr = salt.toString();
        return saltStr;
    }
}
