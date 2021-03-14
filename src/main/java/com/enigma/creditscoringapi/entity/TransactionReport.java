package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Data
public class TransactionReport extends TimeStamp{

    @GeneratedValue(generator = "transaction_report_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "transaction_report_id", strategy = "uuid")
    @Id
    private String id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Approval approval;

    @Column(nullable = false)
    private LocalDate submitDate;

    @Column(nullable = false)
    private LocalDate approvalDate;
}
