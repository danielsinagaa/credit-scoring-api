package com.enigma.creditscoringapi.repository;

import com.enigma.creditscoringapi.entity.Approval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, String> {

    Page<Approval> findAllByStaff(String username, Pageable pageable);

    Page<Approval> findAllNull(Pageable pageable);

    Page<Approval> findAllNotNull(Pageable pageable);

    Page<Approval> findAllApproved(Pageable pageable);

    Page<Approval> findAllRejected(Pageable pageable);

    Approval findNullById(String id);

    Approval findNotNullById(String id);
}
