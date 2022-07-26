package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.model.entity.Experience;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.model.projection.CandidateByTechnologyProjection;
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

import static com.mobydigital.recruiting.utils.Constant.ADDED_TO_THE_LIST;
import static com.mobydigital.recruiting.utils.Constant.ALREADY_EXIST;
import static com.mobydigital.recruiting.utils.Constant.EXPERIENCE;
import static com.mobydigital.recruiting.utils.Constant.EXPERIENCES;
import static com.mobydigital.recruiting.utils.Constant.ID_EQUAL_TO;
import static com.mobydigital.recruiting.utils.Constant.NOT_FOUND;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_SAVED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_SEARCHED;
import static com.mobydigital.recruiting.utils.Constant.SUCCESSFULLY_UPDATED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_CREATED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_DELETED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_SAVED_IN_THE_DATA_BASE;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_SEARCHED;
import static com.mobydigital.recruiting.utils.Constant.WILL_BE_UPDATED;

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
            log.info(EXPERIENCE + WILL_BE_CREATED);
            if (experienceRepository.findByCandidateIdAndTechnologyId(request.getCandidateId(), request.getTechnologyId()) != null) {
                throw new DataAlreadyExistException(EXPERIENCE + ALREADY_EXIST);
            }
            Candidate candidate = candidateService.returnCandidateById(request.getCandidateId());
            Technology technology = technologyService.returnTechnologyById(request.getTechnologyId());

            Experience experience = Experience.builder()
                    .candidate(candidate)
                    .technology(technology)
                    .yearsExperience(request.getYearsExperience())
                    .build();
            log.info(EXPERIENCE + WILL_BE_SAVED_IN_THE_DATA_BASE);
            experienceRepository.save(experience);
            log.info(SUCCESSFULLY_SAVED + EXPERIENCE);
        } catch (DataAlreadyExistException e) {
            log.error(EXPERIENCE + ALREADY_EXIST, e);
            throw new DataAlreadyExistException(e.getMessage());
        }
    }

    @Override
    public void deleteExperienceById(Long id) {
        try {
            log.info(EXPERIENCE + ID_EQUAL_TO + id + WILL_BE_DELETED);
            Optional<Experience> experience = experienceRepository.findById(id);
            if (experience.isEmpty()) {
                throw new NotFoundException(EXPERIENCE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            log.info(EXPERIENCE + WILL_BE_SAVED_IN_THE_DATA_BASE);
            experienceRepository.delete(experience.get());
        } catch (NotFoundException e) {
            log.error(EXPERIENCE + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public void updateExperience(Long id, ExperienceDto request) {
        try {
            log.info(EXPERIENCE + ID_EQUAL_TO + id + WILL_BE_UPDATED);
            Optional<Experience> experience = experienceRepository.findById(id);
            if (experience.isEmpty()) {
                throw new NotFoundException(EXPERIENCE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            Candidate candidate = candidateService.returnCandidateById(request.getCandidateId());
            Technology technology = technologyService.returnTechnologyById(request.getTechnologyId());
            Experience experienceToSaved = Experience.builder()
                    .candidate(candidate)
                    .technology(technology)
                    .yearsExperience(request.getYearsExperience())
                    .build();
            log.info(EXPERIENCE + WILL_BE_SAVED_IN_THE_DATA_BASE);
            experienceRepository.save(experienceToSaved);
            log.info(SUCCESSFULLY_UPDATED + EXPERIENCE);
        } catch (NotFoundException e) {
            log.error(EXPERIENCE + ID_EQUAL_TO + id + NOT_FOUND, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public ExperienceDto getExperienceById(Long id) {
        try {
            log.info(EXPERIENCE + ID_EQUAL_TO + id + WILL_BE_SEARCHED);
            Optional<Experience> experience = experienceRepository.findById(id);
            if (experience.isEmpty()) {
                throw new NotFoundException(EXPERIENCE + ID_EQUAL_TO + id + NOT_FOUND);
            }
            log.info(SUCCESSFULLY_SEARCHED + EXPERIENCE);
            return modelMapper.map(experience.get(), ExperienceDto.class);
        } catch (NotFoundException e) {
            log.error(EXPERIENCE + ID_EQUAL_TO + id + WILL_BE_SEARCHED, e);
            throw new NotFoundException(e.getMessage());
        }
    }

    @Override
    public List<ExperienceDto> getAllExperiences() {
        log.info(EXPERIENCES + WILL_BE_SEARCHED);
        List<Experience> experienceList = experienceRepository.findAll();
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        experienceList.forEach(experience -> {
            log.info(EXPERIENCE + ID_EQUAL_TO + experience.getId() + ADDED_TO_THE_LIST);
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        });
        log.info(SUCCESSFULLY_SEARCHED + EXPERIENCES);
        return experienceDtoList;
    }

    @Override
    public List<ExperienceDto> getAllExperiencesByCandidate(Long id) {
        log.info("All experiences by candidate" + ID_EQUAL_TO + id + WILL_BE_SEARCHED);
        List<Experience> experienceList = experienceRepository.findAllByCandidateId(id);
        List<ExperienceDto> experienceDtoList = new ArrayList<>();
        experienceList.forEach(experience -> {
            log.info("Candidate experience" + ID_EQUAL_TO + id + ADDED_TO_THE_LIST);
            experienceDtoList.add(modelMapper.map(experience, ExperienceDto.class));
        });
        log.info(SUCCESSFULLY_SEARCHED + EXPERIENCES);
        return experienceDtoList;
    }

    @Override
    public List<CandidateByTechnologyProjection> findCandidatesByTechnologyName(String technologyName) {
        return experienceRepository.findByTechnologyName(technologyName);
    }
}
