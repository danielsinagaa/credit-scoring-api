package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Where(clause="is_deleted = 0")
public class Users extends TimeStamp{
    @Id
    @GenericGenerator(name = "id_users", strategy = "uuid2")
    @GeneratedValue(generator = "id_users", strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private Boolean isVerified;

    @Column(nullable = false)
    private String verifiedToken;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column
    private String profilePicture;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Column
    private LocalDate dateRegister;

    @PrePersist
    public void prepersist() {
        dateRegister = LocalDate.now();
        isVerified = false;
    }

}
