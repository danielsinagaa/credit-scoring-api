package com.enigma.creditscoringapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table
@Data
@Where(clause="is_deleted = 0")
public class NeedType extends TimeStamp{

    @JsonIgnore
    @GeneratedValue(generator = "need_type_id", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "need_type_id", strategy = "uuid")
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String type;

    public NeedType(String type) {
        this.type = type;
    }

    public NeedType() {}
}
