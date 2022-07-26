package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.projection.CandidateByTechnologyProjection;

import java.util.List;

public interface ExperienceService {

    void createExperience(ExperienceDto request);

    void deleteExperienceById(Long id);

    void updateExperience(Long id, ExperienceDto request);

    ExperienceDto getExperienceById(Long id);

    List<ExperienceDto> getAllExperiences();

    List<ExperienceDto> getAllExperiencesByCandidate(Long id);

    List<CandidateByTechnologyProjection> findCandidatesByTechnologyName(String technologyName);
}
