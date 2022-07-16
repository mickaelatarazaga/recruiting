package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.entity.Technology;

import java.util.List;

public interface TechnologyService {

    void createTechnology(TechnologyDto request);

    void deleteTechnologyById(Long id);

    void updateTechnology(Long id, TechnologyDto request);

    List<TechnologyDto> getAllTechnologies();

    TechnologyDto getTechnologyById(Long id);

    Technology returnTechnologyById(Long id);
}
