package com.enigma.creditscoringapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class TimeStamp {
    @JsonIgnore
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @JsonIgnore
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        modifiedDate = LocalDateTime.now();
    }
}
