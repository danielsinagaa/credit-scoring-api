package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    private String roles;
    private String fullName;

    public JwtResponse(String token, String username, String email, String roles, String fullName) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.fullName = fullName;
    }
}
