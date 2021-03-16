package com.enigma.creditscoringapi.models;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    private String roles;

    public JwtResponse(String token, String username, String email, String roles) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse() {
    }
}
