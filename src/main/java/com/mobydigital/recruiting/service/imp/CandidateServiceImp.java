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

import static com.mobydigital.recruiting.utils.Constant.ADDED_TO_THE_LIST;
import static com.mobydigital.recruiting.utils.Constant.ALREADY_EXIST;
import static com.mobydigital.recruiting.utils.Constant.CANDIDATE;
import static com.mobydigital.recruiting.utils.Constant.CANDIDATES;
import static com.mobydigital.recruiting.utils.Constant.DNI_EQUAL_TO;
import static com.mobydigital.recruiting.utils.Constant.ID_EQUAL_TO;
import static com.mobydigital.recruiting.utils.Constant.NOT_FOUND;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_DELETED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_SAVED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_SEARCHED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_UPDATED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_CREATED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_DELETED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_DELETED_IN_THE_DATA_BASE;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_SAVED_IN_THE_DATA_BASE;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_SEARCHED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_UPDATED;

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
            log.info(CANDIDATE + WILL_BE_CREATED);
            Optional<Candidate> optionalCandidate = candidateRepository.findByDniNumber(request.getDniNumber());
            if (optionalCandidate.isPresent()) {
                throw new DataAlreadyExistException(CANDIDATE + DNI_EQUAL_TO + request.getDniNumber() + ALREADY_EXIST);
            }
            Candidate candidate = modelMapper.map(request, Candidate.class);
            log.info(CANDIDATE + WILL_BE_SAVED_IN_THE_DATA_BASE);
            candidateRepository.save(candidate);
            log.info(SUCCESSFULLY_SAVED + CANDIDATE);
        } catch (DataAlreadyExistException e) {
            log.error(CANDIDATE + DNI_EQUAL_TO + request.getDniNumber() + ALREADY_EXIST, e);
            throw new DataAlreadyExistException(e.getMessage());
        }
    }

    @Override
    public void deleteCandidateById(Long id) {
        try {
            log.info(CANDIDATE + ID_EQUAL_TO + id + WILL_BE_DELETED);
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            log.info(CANDIDATE + ID_EQUAL_TO + id + WILL_BE_DELETED_IN_THE_DATA_BASE);
            candidate.get().setDeleted(true);
            candidateRepository.save(candidate.get());
            log.info(SUCCESSFULLY_DELETED + CANDIDATE);
        } catch (NotFoundException e) {
            log.error(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateCandidateById(Long id, CandidateDto request) {
        try {
            log.info(CANDIDATE + ID_EQUAL_TO + id + WILL_BE_UPDATED);
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            candidate.get().setFirstName(request.getFirstName());
            candidate.get().setLastName(request.getLastName());
            candidate.get().setTypeOfDni(request.getTypeOfDni());
            candidate.get().setDniNumber(request.getDniNumber());
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            candidate.get().setBirthDate(formatDate.parse(request.getBirthDate()));
            candidateRepository.save(candidate.get());
            log.info(SUCCESSFULLY_UPDATED + CANDIDATE);
        } catch (NotFoundException e) {
            log.error(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        } catch (ParseException e) {
            log.error("Error formatting the date", e.getMessage());
        }
    }

    @Override
    public CandidateDto getCandidateById(Long id) {
        try {
            log.info(CANDIDATE + ID_EQUAL_TO + id + WILL_BE_SEARCHED);
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            log.info(SUCCESSFULLY_SEARCHED + CANDIDATE);
            return modelMapper.map(candidate.get(), CandidateDto.class);
        } catch (NotFoundException e) {
            log.error(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public Candidate returnCandidateById(Long id) {
        try {
            log.info(CANDIDATE + ID_EQUAL_TO + id + WILL_BE_SEARCHED);
            Optional<Candidate> candidate = candidateRepository.findById(id);
            if (candidate.isEmpty()) {
                throw new NotFoundException(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            log.info(SUCCESSFULLY_SEARCHED + CANDIDATE);
            return candidate.get();
        } catch (NotFoundException e) {
            log.error(CANDIDATE + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        log.info(CANDIDATES + WILL_BE_SEARCHED);
        List<Candidate> candidateList = candidateRepository.findAll();
        List<CandidateDto> candidateDtoList = new ArrayList<>();
        candidateList.forEach(candidate -> {
            log.info(CANDIDATE + ID_EQUAL_TO + candidate.getId() + ADDED_TO_THE_LIST);
            candidateDtoList.add(modelMapper.map(candidate, CandidateDto.class));
        });
        log.info(SUCCESSFULLY_SEARCHED + CANDIDATES);
        return candidateDtoList;
    }

}
