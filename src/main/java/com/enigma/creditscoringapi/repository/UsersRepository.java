package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsernameOrEmail(String username, String email);
    List<Users> findByIdIn(List<Long> userIds);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    public Page<Users> findByUsernameContainsOrEmailContains(String username, String email, Pageable pageable);
}
