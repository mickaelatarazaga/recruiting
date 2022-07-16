package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.model.entity.Experience;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.ExperienceRepository;
import com.mobydigital.recruiting.service.CandidateService;
import com.mobydigital.recruiting.service.ExperienceService;
import com.mobydigital.recruiting.service.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
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
    public void createExperience(ExperienceDto request) {
        try {
            log.info("Experience will be created");
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
            log.info("Experience will be saved in the Data Base");
            experienceRepository.save(experience);
            log.info("Successfully Saved Experience");
        } catch (DataAlreadyExistException e) {
            log.error("This Experience already exist", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteExperienceById(Long id) {
        try {
            log.info("Experience Id: " + id + " will be deleted");
            Optional<Experience> experience = experienceRepository.findById(id);
            if (!experience.isPresent()) {
                throw new NotFoundException("Experience " + id + " not found");
            }
            log.info("Experience will be saved in the Data Base");
            experienceRepository.delete(experience.get());
        } catch (NotFoundException e) {
            log.error("Experience " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void updateExperience(Long id, ExperienceDto request) {
        try {
            log.info("Experience id: " + id + " will be updated");
            Optional<Experience> experience = experienceRepository.findById(id);
            if (!experience.isPresent()) {
                throw new NotFoundException("Experience id: " + id + " not found");
            }
            Candidate candidate = candidateService.returnCandidateById(request.getCandidateId());
            Technology technology = technologyService.returnTechnologyById(request.getTechnologyId());

            Experience experienceToSaved = Experience.builder()
                    .candidate(candidate)
                    .technology(technology)
                    .yearsExperience(request.getYearsExperience())
                    .build();
            log.info("Experience will be saved in the Data Base");
            experienceRepository.save(experienceToSaved);
            log.info("Successfully Updated Experience");
        } catch (NotFoundException e) {
            log.error("Experience id: " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ExperienceDto getExperienceById(Long id) {
        try {
            log.info("The experience id: " + id + " will be searched");
            Optional<Experience> experience = experienceRepository.findById(id);
            if (!experience.isPresent()) {
                throw new NotFoundException("Experience " + id + " not found");
            }
            log.info("Experience searched successfully");
            return modelMapper.map(experience.get(), ExperienceDto.class);
        } catch (NotFoundException e) {
            log.error("The experience id: " + id + " will be searched", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ExperienceDto> getAllExperiences() {
        log.info("All experiences will be searched");
        List<Experience> experienceList = experienceRepository.findAll();
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        for (Experience experience : experienceList) {
            log.info("The experience id: " + experience.getId() + " is being added to the list");
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        }
        log.info("Experiences searched successfully");
        return experienceDtoList;
    }

    @Override
    public List<ExperienceDto> getAllExperiencesByCandidate(Long id) {
        log.info("All experiences by candidate id: " + id + " will be searched");
        List<Experience> experienceList = experienceRepository.findAllByCandidateId(id);
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        for (Experience experience : experienceList) {
            log.info("The Candidate experience id: " + id + " is being added to the list");
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        }
        log.info("Experiences searched successfully");
        return experienceDtoList;
    }
}
