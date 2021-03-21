package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
@Where(clause="is_deleted = 0")
public class ContractEmployee extends Customer {
    @GeneratedValue(generator = "contract_employee_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "contract_employee_id", strategy = "uuid")
    @Id
    private String id;

    @Column(nullable = false)
    private LocalDate contractStart;

    @Column(nullable = false)
    private Integer contractLength;

    @Column(nullable = false)
    private LocalDate contractEnd;
}
