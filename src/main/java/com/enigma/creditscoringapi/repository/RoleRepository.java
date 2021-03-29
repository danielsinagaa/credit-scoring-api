package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE role u SET u.is_deleted = true WHERE u.id = ?1")
    void softDelete(String id);

    List<Role> findAllInputCustomer();

    List<Role> findAllReadAllCustomer();

    List<Role> findAllInputTransaction();

    List<Role> findReadAllTransaction();

    List<Role> findAllApproveTransaction();

    List<Role> findAllReadAllReport();

    List<Role> findAllReadAllReportByTransaction();

    List<Role> findAllMaster();
}

