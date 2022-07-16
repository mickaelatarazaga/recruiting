package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.model.dto.ExperienceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ExperienceController {
    ResponseEntity<HttpStatus> saveExperience(@Valid @RequestBody ExperienceDto request);

    ResponseEntity<HttpStatus> deleteExperience(@PathVariable Long id);

    ResponseEntity<HttpStatus> updateTechnology(@PathVariable Long id, @RequestBody ExperienceDto request);

    ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id);

    ResponseEntity<List<ExperienceDto>> getAllExperiences();

    ResponseEntity<List<ExperienceDto>> getAllExperiencesByCandidate(@PathVariable Long id);
}
