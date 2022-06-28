package com.mobydigital.recruiting.repository;

import com.mobydigital.recruiting.model.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Optional<Technology> findByNameAndVersion(String name, String version);
}
