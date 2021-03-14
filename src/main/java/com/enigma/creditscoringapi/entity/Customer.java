package com.enigma.creditscoringapi.entity;

import com.enigma.creditscoringapi.entity.enums.EmployeeType;
import com.enigma.creditscoringapi.entity.enums.NeedType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table
@Data
@NamedQuery(name = "Customer.findAllContract",
    query = "SELECT c FROM Customer c WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.CONTRACT")
@NamedQuery(name = "Customer.findAllNon",
        query = "SELECT c FROM Customer c WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.NON")
@NamedQuery(name = "Customer.findAllRegular",
        query = "SELECT c FROM Customer c WHERE c.employeeType = com.enigma.creditscoringapi.entity.enums.EmployeeType.REGULAR")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends TimeStamp {
    @GeneratedValue(generator = "customer_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "customer_id", strategy = "uuid")
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private EmployeeType employeeType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private NeedType needType;
}
