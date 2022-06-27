package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyRequest;

public interface TechnologyService {

    String createTechnology(TechnologyRequest request) throws DataAlreadyExistException;

    String deleteTechnologyById(Long id) throws NotFoundException;
}
