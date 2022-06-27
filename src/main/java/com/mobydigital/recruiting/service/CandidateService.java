package com.mobydigital.recruiting.service;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateRequest;

import java.text.ParseException;

public interface CandidateService {

    String create(CandidateRequest request) throws DataAlreadyExistException;

    String delete(Long id) throws NotFoundException;

    String update(CandidateRequest request) throws NotFoundException, ParseException;
}
