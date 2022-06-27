package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.model.dto.CandidateRequest;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.repository.CandidateRepository;
import com.mobydigital.recruiting.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImp implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String create(CandidateRequest request) throws DataAlreadyExistException {
        List<Candidate> candidateList = candidateRepository.findAll();
        if (candidateList.stream().anyMatch(candidate -> candidate.getDniNumber().equals(request.getDniNumber()))) {
            throw new DataAlreadyExistException("The Candidate DNI number " + request.getDniNumber() + " already exist");
        }
        Candidate candidate = modelMapper.map(request, Candidate.class);
        candidateRepository.save(candidate);
        return "Successfully Saved Candidate";
    }
}
