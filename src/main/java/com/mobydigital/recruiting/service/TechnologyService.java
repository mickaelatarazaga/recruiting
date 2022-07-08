package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.entity.Technology;

import java.util.List;

public interface TechnologyService {

    String createTechnology(TechnologyDto request) throws DataAlreadyExistException;

    String deleteTechnologyById(Long id) throws NotFoundException;

    String updateTechnology(Long id, TechnologyDto request) throws NotFoundException;

    List<TechnologyDto> getAllTechnologies();

    TechnologyDto getTechnologyById(Long id) throws NotFoundException;

    Technology returnTechnologyById(Long id) throws NotFoundException;
}
