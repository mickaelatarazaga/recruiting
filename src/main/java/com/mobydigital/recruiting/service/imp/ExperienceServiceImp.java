package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.model.entity.Experience;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.CandidateRepository;
import com.mobydigital.recruiting.repository.ExperienceRepository;
import com.mobydigital.recruiting.repository.TechnologyRepository;
import com.mobydigital.recruiting.service.ExperienceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExperienceServiceImp implements ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private TechnologyRepository technologyRepository;

    @Override
    public String createExperience(ExperienceDto request) throws DataAlreadyExistException {
        if (experienceRepository.findByCandidateIdAndTechnologyId(request.getCandidateId(), request.getTechnologyId()) != null) {
            throw new DataAlreadyExistException("This Experience already exist");
        }
        Optional<Candidate> candidate = candidateRepository.findById(request.getCandidateId());
        Optional<Technology> technology = technologyRepository.findById(request.getTechnologyId());

        Experience experience = Experience.builder()
                .candidate(candidate.get())
                .technology(technology.get())
                .yearsExperience(request.getYearsExperience())
                .build();
        experienceRepository.save(experience);

        return "Successfully Saved Experience";
    }
}
