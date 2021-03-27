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
@NamedQuery(name = "TransactionReport.findAllBySubmitter",
query = "SELECT tr FROM TransactionReport tr\n" +
        "JOIN Approval a ON (a.id = tr.approval)\n" +
        "JOIN Transaction t ON (t.id = a.transaction)\n" +
        "WHERE t.submitter =?1\n " +
        "ORDER BY tr.createdDate DESC" )
@NamedQuery(name = "TransactionReport.findAllContract",
        query = "SELECT tr FROM TransactionReport tr\n" +
                "JOIN Approval a ON (a.id = tr.approval)\n" +
                "JOIN Transaction t ON (t.id = a.transaction)\n" +
                "JOIN Customer c ON (c.id = t.customer)\n" +
                "WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.CONTRACT")
@NamedQuery(name = "TransactionReport.findAllNon",
        query = "SELECT tr FROM TransactionReport tr\n" +
                "JOIN Approval a ON (a.id = tr.approval)\n" +
                "JOIN Transaction t ON (t.id = a.transaction)\n" +
                "JOIN Customer c ON (c.id = t.customer)\n" +
                "WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.NON")
@NamedQuery(name = "TransactionReport.findAllRegular",
        query = "SELECT tr FROM TransactionReport tr\n" +
                "JOIN Approval a ON (a.id = tr.approval)\n" +
                "JOIN Transaction t ON (t.id = a.transaction)\n" +
                "JOIN Customer c ON (c.id = t.customer)\n" +
                "WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.REGULAR")
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
