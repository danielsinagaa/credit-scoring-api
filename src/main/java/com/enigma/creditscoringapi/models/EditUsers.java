package com.enigma.creditscoringapi.models;

import lombok.Data;

@Data
public class EditUsers {

    private String email;

    private String fullName;

    private String password;

    private String oldPassword;

    private String profile_password;

    private String username;
}
