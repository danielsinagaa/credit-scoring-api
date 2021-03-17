package com.enigma.creditscoringapi.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
public class Approval extends TimeStamp {
    @GeneratedValue(generator = "approval_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "approval_id", strategy = "uuid")
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Transaction transaction;

    @Column(nullable = false)
    private Boolean approve;

    @Column(nullable = false)
    private String submitter;

    public Approval() {
    }

    public Approval(Transaction transaction, Boolean approve, String submitter) {
        this.transaction = transaction;
        this.approve = approve;
        this.submitter = submitter;
    }
}
