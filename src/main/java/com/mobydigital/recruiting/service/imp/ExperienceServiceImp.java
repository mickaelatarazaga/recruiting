package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.model.entity.Experience;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.ExperienceRepository;
import com.mobydigital.recruiting.service.CandidateService;
import com.mobydigital.recruiting.service.ExperienceService;
import com.mobydigital.recruiting.service.TechnologyService;
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
    private CandidateService candidateService;
    @Autowired
    private TechnologyService technologyService;

    @Override
    public String createExperience(ExperienceDto request) throws DataAlreadyExistException, NotFoundException {
        if (experienceRepository.findByCandidateIdAndTechnologyId(request.getCandidateId(), request.getTechnologyId()) != null) {
            throw new DataAlreadyExistException("This Experience already exist");
        }
        Candidate candidate = candidateService.returnCandidateById(request.getCandidateId());
        Technology technology = technologyService.returnTechnologyById(request.getTechnologyId());

        Experience experience = Experience.builder()
                .candidate(candidate)
                .technology(technology)
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
        Candidate candidate = candidateService.returnCandidateById(request.getCandidateId());
        Technology technology = technologyService.returnTechnologyById(request.getTechnologyId());

        Experience experienceToSaved = Experience.builder()
                .candidate(candidate)
                .technology(technology)
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
        Candidate candidate = candidateService.returnCandidateById(id);
        List<Experience> experienceList = experienceRepository.findAllByCandidateId(id);
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        for (Experience experience : experienceList) {
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        }
        return experienceDtoList;
    }
}
