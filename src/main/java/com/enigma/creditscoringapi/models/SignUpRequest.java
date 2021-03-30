package com.enigma.creditscoringapi.models;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String role;

    private String fullName;

    private String profilePicture;

    private String password;
}
