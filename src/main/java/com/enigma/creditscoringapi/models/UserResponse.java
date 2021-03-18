package com.enigma.creditscoringapi.models;

import com.enigma.creditscoringapi.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserResponse {
    private String id;

    private String username;

    private String fullName;

    private String email;

    private Boolean isVerified;

    private Boolean active;

    private String profilePicture;

    private Set<Role> roles = new HashSet<>();

    private LocalDate dateRegister;

}
