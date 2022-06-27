package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.model.dto.CandidateRequest;

public interface CandidateService {

    String create(CandidateRequest request) throws DataAlreadyExistException;
}
