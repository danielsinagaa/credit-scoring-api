package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.models.JwtResponse;
import com.enigma.creditscoringapi.models.LoginRequest;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.UsersRepository;
import com.enigma.creditscoringapi.security.jwt.JwtUtils;
import com.enigma.creditscoringapi.security.service.UserDetailsImpl;
import com.enigma.creditscoringapi.services.RoleService;
import com.enigma.creditscoringapi.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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

    @PostMapping("/login")
    public ResponseMessage authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Users user = repository.getByUsername(loginRequest.getUsername());
        if (user == null) {
            user = repository.getByEmail(loginRequest.getUsername());
            if (user == null) {
                return new ResponseMessage(403, "no username or email found", null);
            }
        }

        if (!user.getIsVerified()) {
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

        Role role = service.findRoleByName(roles.get(0));

        JwtResponse response = new JwtResponse(
                jwt,
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles.get(0),
                user.getFullName(),
                user.getId(),
                role.getInputCustomer(),
                role.getReadAllCustomer(),
                role.getInputTransaction(),
                role.getReadAllTransaction(),
                role.getApproveTransaction(),
                role.getReadAllReport(),
                role.getReadAllReportByTransaction(),
                role.getMaster());

        return ResponseMessage.success(response);
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

}
