package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;

import java.util.List;

public interface ExperienceService {

    String createExperience(ExperienceDto request) throws DataAlreadyExistException, NotFoundException;

    String deleteExperienceById(Long id) throws NotFoundException;

    String updateExperience(Long id, ExperienceDto request) throws NotFoundException;

    ExperienceDto getExperienceById(Long id) throws NotFoundException;

    List<ExperienceDto> getAllExperiences();

    List<ExperienceDto> getAllExperiencesByCandidate(Long id) throws NotFoundException;
}
