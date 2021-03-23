package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String username;
    private String email;
    private String roles;
    private String fullName;
    private String id;

    public JwtResponse(String token, String username, String email, String roles, String fullName, String id) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.fullName = fullName;
        this.id = id;
    }
}
