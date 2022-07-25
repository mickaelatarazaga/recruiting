package com.mobydigital.recruiting.repository;

import com.mobydigital.recruiting.model.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mobydigital.recruiting.utils.Query.FIND_CANDIDATE_BY_ID;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    @Query(value = FIND_CANDIDATE_BY_ID, nativeQuery = true)
    Optional<Candidate> findByDniNumber(String dniNumber);
}
