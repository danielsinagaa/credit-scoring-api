package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

