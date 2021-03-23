package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.NeedType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NeedTypeRepository extends JpaRepository<NeedType, String> {
}
