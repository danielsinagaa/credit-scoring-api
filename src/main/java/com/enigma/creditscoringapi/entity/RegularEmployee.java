package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class RegularEmployee extends Customer {
    @GeneratedValue(generator = "regular_employee_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "regular_employee_id", strategy = "uuid")
    @Id
    private String id;
}
