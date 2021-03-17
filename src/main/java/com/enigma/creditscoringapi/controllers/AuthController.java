package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.entity.enums.ERole;
import com.enigma.creditscoringapi.models.JwtResponse;
import com.enigma.creditscoringapi.models.LoginRequest;
import com.enigma.creditscoringapi.models.SignUpRequest;
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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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

        if (!user.getActive() || !user.getIsVerified()) {
            return new ResponseMessage(403, "account has not been verified yet", null);
        }

        if (encoder.matches(user.getPassword(), loginRequest.getPassword())) {
            return new ResponseMessage(403, "your password is wrong", null);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

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
                roles.get(0));

        return ResponseMessage.success(response);
    }

    @GetMapping("/verification/{token}")
    public ResponseMessage verification(@PathVariable String token) {
        Users user = usersService.findByToken(token);

        if (user == null) {
            return new ResponseMessage(400, "Verification token is not valid.", null);
        } else {
            System.out.println(user.toString());
            user.setIsVerified(true);
            usersService.save(user);
            return new ResponseMessage(200, "Verification token is success.", null);
        }
    }

    @PostMapping("/signup")
    public ResponseMessage registerUser(@Valid @RequestBody SignUpRequest request) {

        if (repository.existsByUsername(request.getUsername())) {

            return new ResponseMessage(409, "not allowed", "ERROR: username is already use");
        }

        if (repository.existsByEmail(request.getEmail())) {
            return new ResponseMessage(409, "not allowed", "ERROR: email is already use");
        }

        String token = generateVerificationToken();

        Users user = modelMapper.map(request, Users.class);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setIsVerified(false);
        user.setVerifiedToken(token);

        String strRoles = request.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role staff = service.findRoleByName(ERole.STAFF);
            roles.add(staff);
        } else if (strRoles.contains("MASTER")) {
            Role supervisor = service.findRoleByName(ERole.MASTER);
            roles.add(supervisor);
        } else {
            Role master = service.findRoleByName(ERole.SUPERVISOR);
            roles.add(master);
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
}
