package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.model.dto.TechnologyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface TechnologyController {
    ResponseEntity<HttpStatus> saveTechnology(@Valid @RequestBody TechnologyDto request);

    ResponseEntity<HttpStatus> deleteCandidate(@PathVariable Long id);

    ResponseEntity<HttpStatus> updateTechnology(@PathVariable Long id, @RequestBody TechnologyDto request);

    ResponseEntity<TechnologyDto> getTechnologyById(@PathVariable Long id);

    ResponseEntity<List<TechnologyDto>> getAllTechnologies();
}
