package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.model.dto.CandidateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface CandidateController {

    ResponseEntity<HttpStatus> saveCandidate(@Valid @RequestBody CandidateDto request);

    ResponseEntity<HttpStatus> deleteCandidate(@PathVariable Long id);

    ResponseEntity<HttpStatus> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDto request);

    ResponseEntity<CandidateDto> getCandidate(@PathVariable Long id);

    ResponseEntity<List<CandidateDto>> getAllCandidates();
}
