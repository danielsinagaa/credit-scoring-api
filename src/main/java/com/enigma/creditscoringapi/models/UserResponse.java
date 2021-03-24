package com.enigma.creditscoringapi.models;

import com.enigma.creditscoringapi.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponse {
    private String id;

    private String username;

    private String fullName;

    private String email;

    private Boolean isVerified;

    private String profilePicture;

    @JsonIgnore
    private List<Role> roles;

    private String role;

    private LocalDate dateRegister;

}
