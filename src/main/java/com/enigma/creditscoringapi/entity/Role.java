package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NamedQuery(name = "Role.findAllInputCustomer",
        query = "SELECT r FROM Role r " +
                "WHERE r.inputCustomer = true")
@NamedQuery(name = "Role.findAllReadAllCustomer",
        query = "SELECT r FROM Role r " +
                "WHERE r.readAllCustomer = true")
@NamedQuery(name = "Role.findAllInputTransaction",
        query = "SELECT r FROM Role r " +
                "WHERE r.inputTransaction = true")
@NamedQuery(name = "Role.findReadAllTransaction",
        query = "SELECT r FROM Role r " +
                "WHERE r.readAllTransaction = true")
@NamedQuery(name = "Role.findAllApproveTransaction",
        query = "SELECT r FROM Role r " +
                "WHERE r.approveTransaction = true")
@NamedQuery(name = "Role.findAllReadAllReport",
        query = "SELECT r FROM Role r " +
                "WHERE r.readAllReport = true")
@NamedQuery(name = "Role.findAllReadAllReportByTransaction",
        query = "SELECT r FROM Role r " +
                "WHERE r.readAllReportByTransaction = true")
@NamedQuery(name = "Role.findAllMaster",
        query = "SELECT r FROM Role r " +
                "WHERE r.master = true")
@Entity
@Table
@Data
public class Role extends TimeStamp{
    @Id
    @GenericGenerator(name = "id_role", strategy = "uuid2")
    @GeneratedValue(generator = "id_role", strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean inputCustomer;

    @Column(nullable = false)
    private Boolean readAllCustomer;

    @Column(nullable = false)
    private Boolean inputTransaction;

    @Column(nullable = false)
    private Boolean readAllTransaction;

    @Column(nullable = false)
    private Boolean approveTransaction;

    @Column(nullable = false)
    private Boolean readAllReport;

    @Column(nullable = false)
    private Boolean readAllReportByTransaction;

    private Boolean master;

    @PrePersist
    void prePersit(){
        master = false;
    }

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

}
