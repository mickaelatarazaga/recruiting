package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateRequest;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.repository.CandidateRepository;
import com.mobydigital.recruiting.service.CandidateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

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

    @Override
    public String delete(Long id) throws NotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidate " + id + " not found");
        }
        candidate.get().setDeleted(true);
        candidateRepository.save(candidate.get());
        return "Successfully deleted Candidate";
    }

    @Override
    public String update(CandidateRequest request) throws NotFoundException, ParseException {
        Optional<Candidate> candidate = candidateRepository.findByDniNumber(request.getDniNumber());
        if (!candidate.isPresent()) {
            throw new NotFoundException("The Candidate DNI number " + request.getDniNumber() + " not found");
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

}
