package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class NonEmployee extends Customer {
    @GeneratedValue(generator = "non_employee_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "non_employee_id", strategy = "uuid")
    @Id
    private String id;
}
