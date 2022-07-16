package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.repository.CandidateRepository;
import com.mobydigital.recruiting.service.CandidateService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CandidateServiceImp implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createCandidate(CandidateDto request) {
        try {
            log.info("Candidate will be created");

            List<Candidate> candidateList = candidateRepository.findAll();
            if (candidateList.stream().anyMatch(candidate -> candidate.getDniNumber().equals(request.getDniNumber()))) {
                throw new DataAlreadyExistException("The Candidate DNI number " + request.getDniNumber() + " already exist");
            }
            Candidate candidate = modelMapper.map(request, Candidate.class);
            log.info("Candidate will be saved in the Data Base");
            candidateRepository.save(candidate);
            log.info("Successfully Saved Candidate");
        } catch (DataAlreadyExistException e) {
            log.error("DNI: " + request.getDniNumber() + " already exist", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteCandidateById(Long id) {
        try {
            log.info("Soft delete of Candidate Id: " + id);
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException("Candidate " + id + " not found");
            }
            candidate.get().setDeleted(true);
            candidateRepository.save(candidate.get());
            log.info("Successfully deleted Candidate");
        } catch (NotFoundException e) {
            log.error("Candidate " + id + " not found", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCandidateByDni(Long id, CandidateDto request) {
        try {
            log.info("Candidate id: " + id + " will be updated");
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException("The Candidate ID number " + id + " not found");
            }
            candidate.get().setFirstName(request.getFirstName());
            candidate.get().setLastName(request.getLastName());
            candidate.get().setTypeOfDni(request.getTypeOfDni());
            candidate.get().setDniNumber(request.getDniNumber());
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            candidate.get().setBirthday(formatDate.parse(request.getBirthday()));
            candidateRepository.save(candidate.get());
            log.info("Successfully updated Candidate");
        } catch (NotFoundException e) {
            log.error("Candidate ID number " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        } catch (ParseException e) {
            log.error("Error formatting the date", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CandidateDto getCandidateById(Long id) {
        try {
            log.info("The candidate id: " + id + " will be searched");
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException("Candidate " + id + " not found");
            }
            log.info("Candidate searched successfully");
            return modelMapper.map(candidate.get(), CandidateDto.class);
        } catch (NotFoundException e) {
            log.error("Candidate " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Candidate returnCandidateById(Long id) {
        log.info("The candidate id: " + id + " will be searched");
        try {
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException("Candidate " + id + " not found");
            }
            log.info("Candidate searched successfully");
            return candidate.get();
        } catch (NotFoundException e) {
            log.error("Candidate " + id + " not found", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        log.info("All candidates will be searched");
        List<Candidate> candidateList = candidateRepository.findAll();
        List<CandidateDto> candidateDtoList = new ArrayList<>();
        candidateList.forEach(candidate -> {
            log.info("The candidate id: " + candidate.getId() + " is being added to the list");
            candidateDtoList.add(modelMapper.map(candidate, CandidateDto.class));
        });
        log.info("Candidates searched successfully");
        return candidateDtoList;
    }

}
