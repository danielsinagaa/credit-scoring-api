package com.enigma.creditscoringapi.entity;

import com.enigma.creditscoringapi.entity.enums.ERole;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Users {
    @Id
    @GenericGenerator(name = "id_users",strategy = "uuid2")
    @GeneratedValue(generator = "id_users",strategy = GenerationType.IDENTITY)
    private String id;

    @NotBlank
    @Size(min = 4, max = 20)
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private Boolean isVerified;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column
    private LocalDate dateRegister;

    @PrePersist
    public void prepersist(){
        dateRegister = LocalDate.now();
        isVerified = false;
    }
}
