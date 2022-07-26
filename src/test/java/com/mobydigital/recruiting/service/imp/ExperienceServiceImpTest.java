package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.entity.Experience;
import com.mobydigital.recruiting.repository.ExperienceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.mobydigital.recruiting.util.TestUtil.getCandidateId;
import static com.mobydigital.recruiting.util.TestUtil.getExperience;
import static com.mobydigital.recruiting.util.TestUtil.getExperienceDto;
import static com.mobydigital.recruiting.util.TestUtil.getExperienceId;
import static com.mobydigital.recruiting.util.TestUtil.getListExperience;
import static com.mobydigital.recruiting.util.TestUtil.getOptionalCandidate;
import static com.mobydigital.recruiting.util.TestUtil.getOptionalExperience;
import static com.mobydigital.recruiting.util.TestUtil.getOptionalTechnology;
import static com.mobydigital.recruiting.util.TestUtil.getTechnologyId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExperienceServiceImpTest {
    @InjectMocks
    private ExperienceServiceImp experienceService;
    @Mock
    private ExperienceRepository experienceRepository;
    @Mock
    private CandidateServiceImp candidateServiceImp;
    @Mock
    private TechnologyServiceImp technologyServiceImp;

    @Mock
    private ModelMapper modelMapper;


    @DisplayName("Successfully searched all experiences")
    @Test
    void getAllExperiences_SuccessfullySearched() {
        when(experienceRepository.findAll()).thenReturn(getListExperience());
        when(modelMapper.map(getOptionalExperience().get(), ExperienceDto.class)).thenReturn(getExperienceDto());
        List<ExperienceDto> experienceSearched = experienceService.getAllExperiences();
        verify(experienceRepository, times(1)).findAll();
        assertEquals(getExperienceDto(), experienceSearched.get(0));
    }

    @DisplayName("Successfully searched all experiences by Candidate")
    @Test
    void getAllExperiencesByCandidate_SuccessfullySearched() {
        when(experienceRepository.findAllByCandidateId(getCandidateId())).thenReturn(getListExperience());
        when(modelMapper.map(getOptionalExperience().get(), ExperienceDto.class)).thenReturn(getExperienceDto());
        List<ExperienceDto> experienceSearched = experienceService.getAllExperiencesByCandidate(getCandidateId());
        verify(experienceRepository, times(1)).findAllByCandidateId(getCandidateId());
        assertEquals(getExperienceDto(), experienceSearched.get(0));
    }

    @DisplayName("Check createExperience Method")
    @Nested
    class CheckCreateExperience {
        @DisplayName("Successfully saved experience")
        @Test
        void createExperience_SuccessfullySaved() {
            when(experienceRepository.findByCandidateIdAndTechnologyId(getCandidateId(), getTechnologyId())).thenReturn(null);
            when(experienceRepository.save(any(Experience.class))).thenReturn(getExperience());
            when(candidateServiceImp.returnCandidateById(getCandidateId())).thenReturn(getOptionalCandidate().get());
            when(technologyServiceImp.returnTechnologyById(getTechnologyId())).thenReturn(getOptionalTechnology().get());
            when(modelMapper.map(getExperienceDto(), Experience.class)).thenReturn(getExperience());
            experienceService.createExperience(getExperienceDto());
            verify(experienceRepository, times(1)).findByCandidateIdAndTechnologyId(getCandidateId(), getTechnologyId());
            verify(experienceRepository, times(1)).save(any(Experience.class));
        }


        @DisplayName("Return DataAlreadyExistException when the experience by Candidate already exist")
        @Test
        void createExperience_Exception() {
            when(experienceRepository.findByCandidateIdAndTechnologyId(getCandidateId(), getTechnologyId())).thenReturn(getExperience());
            assertThrows(DataAlreadyExistException.class, () -> experienceService.createExperience(getExperienceDto()));

        }
    }

    @DisplayName("Check deleteExperienceById Method")
    @Nested
    class CheckDeleteExperienceById {
        @DisplayName("Successfully deleted experience")
        @Test
        void deleteExperienceById_SuccessfullyDeleted() {
            when(experienceRepository.findById(getExperienceId())).thenReturn(getOptionalExperience());
            experienceService.deleteExperienceById(getExperienceId());
            verify(experienceRepository, times(1)).findById(getExperienceId());
            verify(experienceRepository, times(1)).delete(any(Experience.class));
        }

        @DisplayName("Return NotFoundException when the Id Experience's not found")
        @Test
        void deleteExperienceById_Exception() {
            when(experienceRepository.findById(getExperienceId())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> experienceService.deleteExperienceById(getExperienceId()));

        }
    }

    @DisplayName("Check updateExperience Method")
    @Nested
    class CheckUpdateExperience {
        @DisplayName("Successfully updated experience")
        @Test
        void updateExperience_SuccessfullyUpdated() {
            when(experienceRepository.findById(getExperienceId())).thenReturn(getOptionalExperience());
            when(experienceRepository.save(any(Experience.class))).thenReturn(getOptionalExperience().get());
            experienceService.updateExperience(getExperienceId(), getExperienceDto());
            verify(experienceRepository, times(1)).findById(getExperienceId());
            verify(experienceRepository, times(1)).save(any(Experience.class));
        }

        @DisplayName("Return NotFoundException when the Id Experience's not found")
        @Test
        void updateExperience_NotFoundException() {
            when(experienceRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> experienceService.updateExperience(getCandidateId(), getExperienceDto()));

        }
    }

    @DisplayName("Check getExperienceById Method")
    @Nested
    class CheckGetCandidateById {
        @DisplayName("Successfully searched experience")
        @Test
        void getExperienceById_SuccessfullySearched() {
            when(experienceRepository.findById(getExperienceId())).thenReturn(getOptionalExperience());
            when(modelMapper.map(getOptionalExperience().get(), ExperienceDto.class)).thenReturn(getExperienceDto());
            ExperienceDto experienceSearched = experienceService.getExperienceById(getExperienceId());
            verify(experienceRepository, times(1)).findById(getExperienceId());
            assertNotNull(experienceSearched);
        }

        @DisplayName("Return NotFoundException when the Id Experience's not found")
        @Test
        void getCandidateById_Exception() {
            when(experienceRepository.findById(getExperienceId())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> experienceService.getExperienceById(getExperienceId()));
        }
    }


}