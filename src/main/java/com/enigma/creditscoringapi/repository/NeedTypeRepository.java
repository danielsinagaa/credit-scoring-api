package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.NeedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NeedTypeRepository extends JpaRepository<NeedType, String> {

    Boolean existsByType(String type);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE need_type u SET u.is_deleted = true WHERE u.id = ?1")
    void softDelete(String id);
}
