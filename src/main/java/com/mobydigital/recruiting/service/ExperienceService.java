package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;

public interface ExperienceService {

    String createExperience(ExperienceDto request) throws DataAlreadyExistException;

    String deleteExperienceById(Long id) throws NotFoundException;
}
