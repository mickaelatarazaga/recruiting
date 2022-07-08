package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.entity.Candidate;

import java.text.ParseException;
import java.util.List;

public interface CandidateService {

    String createCandidate(CandidateDto request) throws DataAlreadyExistException;

    String deleteCandidateById(Long id) throws NotFoundException;

    String updateCandidateByDni(Long id, CandidateDto request) throws NotFoundException, ParseException;

    CandidateDto getCandidateById(Long id) throws NotFoundException;

    Candidate returnCandidateById(Long id) throws NotFoundException;

    List<CandidateDto> getAllCandidates();
}
