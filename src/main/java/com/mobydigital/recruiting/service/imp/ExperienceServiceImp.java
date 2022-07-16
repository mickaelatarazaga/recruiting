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
    public String createExperience(ExperienceDto request) {
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
            return "Successfully Saved Experience";
        } catch (DataAlreadyExistException e) {
            log.error("This Experience already exist", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String deleteExperienceById(Long id) {
        try {
            Optional<Experience> experience = experienceRepository.findById(id);
            if (!experience.isPresent()) {
                throw new NotFoundException("Experience " + id + " not found");
            }
            experienceRepository.delete(experience.get());
            return "Successfully deleted Experience";
        } catch (NotFoundException e) {
            log.error("Experience " + id + " not found", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String updateExperience(Long id, ExperienceDto request) {
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
            return "Successfully Updated Experience";
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
            ExperienceDto experienceDto = modelMapper.map(experience.get(), ExperienceDto.class);
            log.info("Experience searched successfully");
            return experienceDto;
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
            log.info("The experience is being added to the list");
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        }
        log.info("Experiences searched successfully");
        return experienceDtoList;
    }

    @Override
    public List<ExperienceDto> getAllExperiencesByCandidate(Long id) {
        log.info("All experiences by candidate id: " + id + " will be searched");
        Candidate candidate = candidateService.returnCandidateById(id);
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
