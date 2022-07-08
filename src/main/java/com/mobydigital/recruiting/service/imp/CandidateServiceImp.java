package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.repository.CandidateRepository;
import com.mobydigital.recruiting.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImp implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String createCandidate(CandidateDto request) throws DataAlreadyExistException {
        List<Candidate> candidateList = candidateRepository.findAll();
        if (candidateList.stream().anyMatch(candidate -> candidate.getDniNumber().equals(request.getDniNumber()))) {
            throw new DataAlreadyExistException("The Candidate DNI number " + request.getDniNumber() + " already exist");
        }
        Candidate candidate = modelMapper.map(request, Candidate.class);
        candidateRepository.save(candidate);
        return "Successfully Saved Candidate";
    }

    @Override
    public String deleteCandidateById(Long id) throws NotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidate " + id + " not found");
        }
        candidate.get().setDeleted(true);
        candidateRepository.save(candidate.get());
        return "Successfully deleted Candidate";
    }

    @Override
    public String updateCandidateByDni(Long id, CandidateDto request) throws NotFoundException, ParseException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new NotFoundException("The Candidate ID number " + id + " not found");
        }
        candidate.get().setFirstName(request.getFirstName());
        candidate.get().setLastName(request.getLastName());
        candidate.get().setTypeOfDni(request.getTypeOfDni());
        candidate.get().setDniNumber(request.getDniNumber());
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        candidate.get().setBirthday(formatDate.parse(request.getBirthday()));
        candidateRepository.save(candidate.get());
        return "Successfully updated Candidate";
    }

    @Override
    public CandidateDto getCandidateById(Long id) throws NotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidate " + id + " not found");
        }
        CandidateDto candidateDto = modelMapper.map(candidate.get(), CandidateDto.class);
        return candidateDto;
    }

    @Override
    public Candidate returnCandidateById(Long id) throws NotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidate " + id + " not found");
        }
        return candidate.get();
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        List<Candidate> candidateList = candidateRepository.findAll();
        List<CandidateDto> candidateDtoList = new ArrayList<>();
        for (Candidate candidate : candidateList) {
            candidateDtoList.add(modelMapper.map(candidate, CandidateDto.class));
        }
        return candidateDtoList;
    }

}
