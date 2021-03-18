package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Users extends TimeStamp{
    @Id
    @GenericGenerator(name = "id_users", strategy = "uuid2")
    @GeneratedValue(generator = "id_users", strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    @Size(min = 4, max = 20)
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private Boolean isVerified;

    @Column
    private Boolean active;

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
    private Set<Role> roles = new HashSet<>();

    @Column
    private LocalDate dateRegister;

    @PrePersist
    public void prepersist() {
        dateRegister = LocalDate.now();
        isVerified = false;
        profilePicture = "https://res.cloudinary.com/nielnaga/image/upload/v1615870303/download-removebg-preview_zyrump.png";
        active = false;
    }

}
