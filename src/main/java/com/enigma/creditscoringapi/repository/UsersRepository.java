package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE verified_token = :token")
    Users findByVerifiedToken(String token);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE username = :username")
    Users getByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE email = :email")
    Users getByEmail(String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE users u SET u.is_deleted = true WHERE u.id = ?1")
    void softDelete(String id);

    Page<Users> findAllVerified(Pageable pageable);

    Page<Users> findAllNotVerified(Pageable pageable);
}
