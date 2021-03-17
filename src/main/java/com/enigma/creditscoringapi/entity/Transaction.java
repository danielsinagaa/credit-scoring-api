package com.enigma.creditscoringapi.entity;

import com.enigma.creditscoringapi.entity.enums.NeedType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class Transaction extends TimeStamp{
    @GeneratedValue(generator = "transaction_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "transaction_id", strategy = "uuid")
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Customer customer;

    @Column(nullable = false)
    private Integer income;

    @Column(nullable = false)
    private Integer outcome;

    @Column(nullable = false)
    private Integer loan;//pinjaman

    @Column(nullable = false)
    private Integer tenor;

    @Column(nullable = false)
    private Double mainLoan;//pokok

    @Column(nullable = false)
    private Double interest;//bunga

    @Column(nullable = false)
    private Double creditRatio;

    @Column(nullable = false)
    private Double installment;//angsuran

    @Column(nullable = false)
    private Double installmentTotal;

    @Column(nullable = false)
    private Integer interestRate;

    @Column(nullable = false)
    private String submitter;

    @Column(nullable = false)
    private Boolean financeCriteria;

    @Column(nullable = false)
    private String notes;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private NeedType needType;

    @Column
    private Boolean employeeCriteria;
}
