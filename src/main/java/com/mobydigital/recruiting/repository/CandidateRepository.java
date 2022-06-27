package com.mobydigital.recruiting.repository;

import com.mobydigital.recruiting.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByDniNumber(String dni);
}
