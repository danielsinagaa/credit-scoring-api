package com.enigma.creditscoringapi.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type="Bearer";
    private String id;
    private String username;
    private String email;
    private LocalDate dateRegister;
    private List<String> roles;

    public JwtResponse(String token, String id, String username, String email, LocalDate dateRegister, List<String> roles) {
        this.token = token;
        this.type = type;
        this.id = id;
        this.username = username;
        this.email = email;
        this.dateRegister = dateRegister;
        this.roles = roles;
    }

    public JwtResponse() {
    }
}
