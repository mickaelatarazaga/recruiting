package com.mobydigital.recruiting.repository;

import com.mobydigital.recruiting.model.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.mobydigital.recruiting.utils.Query.FIND_TECHNOLOGY_BY_NAME_AND_VERSION;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    @Query(value = FIND_TECHNOLOGY_BY_NAME_AND_VERSION, nativeQuery = true)
    Optional<Technology> findByNameAndVersion(String name, String version);
}
