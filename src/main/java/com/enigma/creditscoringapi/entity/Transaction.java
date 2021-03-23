package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table
@Data
@Where(clause="is_deleted = 0")
@NamedQuery(name = "Transaction.findAllContract",
        query = "SELECT t FROM Transaction t\n" +
                "JOIN Customer c ON (c.id = t.customer)\n" +
                "WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.CONTRACT")
@NamedQuery(name = "Transaction.findAllNon",
        query = "SELECT t FROM Transaction t\n" +
                "JOIN Customer c ON (c.id = t.customer)\n" +
                "WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.NON")
@NamedQuery(name = "Transaction.findAllRegular",
        query = "SELECT t FROM Transaction t\n" +
                "JOIN Customer c ON (c.id = t.customer)\n" +
                "WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.REGULAR")
public class Transaction extends TimeStamp{

    @GeneratedValue(generator = "transaction_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "transaction_id", strategy = "uuid")
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Customer customer;

    @ManyToOne
    @JoinColumn(nullable = false)
    NeedType needType;

    @Column(nullable = false)
    private Integer income;

    @Column(nullable = false)
    private Integer outcome;

    @Column(nullable = false)
    private Integer loan;

    @Column(nullable = false)
    private Integer tenor;

    @Column(nullable = false)
    private Double mainLoan;

    @Column(nullable = false)
    private Double interest;

    @Column(nullable = false)
    private Double creditRatio;

    @Column(nullable = false)
    private Double installment;

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

    @Column
    private Boolean employeeCriteria;
}
