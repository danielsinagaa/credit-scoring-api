package com.enigma.creditscoringapi.entity;

import com.enigma.creditscoringapi.entity.enums.ERole;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class Role {
    @Id
    @GenericGenerator(name = "id_role", strategy = "uuid2")
    @GeneratedValue(generator = "id_role", strategy = GenerationType.IDENTITY)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole name;

    public Role() {
    }

    public Role(ERole name) {
        this.name = name;
    }

}
