package com.mobydigital.recruiting.repository;

import com.mobydigital.recruiting.model.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import static com.mobydigital.recruiting.utils.Query.FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query(value = FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID, nativeQuery = true)
    Experience findByCandidateIdAndTechnologyId(Long idCandidate, Long idTechnology);

}
