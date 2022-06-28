package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyDto;

public interface TechnologyService {

    String createTechnology(TechnologyDto request) throws DataAlreadyExistException;

    String deleteTechnologyById(Long id) throws NotFoundException;

    String updateTechnology(Long id, TechnologyDto request) throws NotFoundException;
}
