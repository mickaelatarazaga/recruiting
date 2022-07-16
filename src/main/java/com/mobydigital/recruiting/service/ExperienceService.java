package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.model.dto.ExperienceDto;

import java.util.List;

public interface ExperienceService {

    String createExperience(ExperienceDto request);

    String deleteExperienceById(Long id);

    String updateExperience(Long id, ExperienceDto request);

    ExperienceDto getExperienceById(Long id);

    List<ExperienceDto> getAllExperiences();

    List<ExperienceDto> getAllExperiencesByCandidate(Long id);
}
