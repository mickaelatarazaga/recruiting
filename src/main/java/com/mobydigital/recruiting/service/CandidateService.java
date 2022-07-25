package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.entity.Candidate;

import java.util.List;

public interface CandidateService {

    void createCandidate(CandidateDto request);

    void deleteCandidateById(Long id);

    void updateCandidateById(Long id, CandidateDto request);

    CandidateDto getCandidateById(Long id);

    Candidate returnCandidateById(Long id);

    List<CandidateDto> getAllCandidates();
}
