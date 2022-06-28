package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
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

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public String deleteExperienceById(Long id) throws NotFoundException {
        Optional<Experience> experience = experienceRepository.findById(id);
        if (!experience.isPresent()) {
            throw new NotFoundException("Experience " + id + " not found");
        }
        experienceRepository.delete(experience.get());
        return "Successfully deleted Technology";
    }

    @Override
    public String updateExperience(Long id, ExperienceDto request) throws NotFoundException {
        Optional<Experience> experience = experienceRepository.findById(id);
        if (!experience.isPresent()) {
            throw new NotFoundException("Experience not found");
        }
        Optional<Candidate> candidate = candidateRepository.findById(request.getCandidateId());
        Optional<Technology> technology = technologyRepository.findById(request.getTechnologyId());
        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidate ID not found");
        }
        if (!technology.isPresent()) {
            throw new NotFoundException("Technology ID not found");
        }
        Experience experienceToSaved = Experience.builder()
                .candidate(candidate.get())
                .technology(technology.get())
                .yearsExperience(request.getYearsExperience())
                .build();
        experienceRepository.save(experienceToSaved);
        return "Successfully Updated Experience";
    }

    @Override
    public ExperienceDto getExperienceById(Long id) throws NotFoundException {
        Optional<Experience> experience = experienceRepository.findById(id);
        if (!experience.isPresent()) {
            throw new NotFoundException("Experience " + id + " not found");
        }
        ExperienceDto experienceDto = modelMapper.map(experience.get(), ExperienceDto.class);
        return experienceDto;
    }

    @Override
    public List<ExperienceDto> getAllExperiences() {
        List<Experience> experienceList = experienceRepository.findAll();
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        for (Experience experience : experienceList) {
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        }
        return experienceDtoList;
    }

    @Override
    public List<ExperienceDto> getAllExperiencesByCandidate(Long id) throws NotFoundException {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (!candidate.isPresent()) {
            throw new NotFoundException("Candidate not found");
        }
        List<Experience> experienceList = experienceRepository.findAllByCandidateId(id);
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        for (Experience experience : experienceList) {
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        }
        return experienceDtoList;
    }
}
