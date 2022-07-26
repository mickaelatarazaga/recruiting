package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.repository.CandidateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.mobydigital.recruiting.util.TestUtil.getCandidateDto;
import static com.mobydigital.recruiting.util.TestUtil.getCandidateId;
import static com.mobydigital.recruiting.util.TestUtil.getOptionalCandidate;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CandidateServiceImpTest {
    @InjectMocks
    private CandidateServiceImp candidateService;
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private ModelMapper modelMapper;


    @DisplayName("Check createCandidate Method")
    @Nested
    class CheckCreateCandidate {
        @DisplayName("Successfully saved candidate")
        @Test
        void createCandidate_SuccessfullySaved() {
            when(candidateRepository.findByDniNumber(getCandidateDto().getDniNumber())).thenReturn(Optional.ofNullable(null));
            when(candidateRepository.save(any(Candidate.class))).thenReturn(getOptionalCandidate().get());
            when(modelMapper.map(getCandidateDto(), Candidate.class)).thenReturn(getOptionalCandidate().get());
            candidateService.createCandidate(getCandidateDto());
            verify(candidateRepository, times(1)).findByDniNumber(getCandidateDto().getDniNumber());
            verify(candidateRepository, times(1)).save(any(Candidate.class));
        }

        @DisplayName("Return DataAlreadyExistException when the DNI Candidate's already exist")
        @Test
        void createCandidate_Exception() {
            when(candidateRepository.findByDniNumber(getCandidateDto().getDniNumber())).thenReturn(getOptionalCandidate());
            assertThrows(DataAlreadyExistException.class, () -> candidateService.createCandidate(getCandidateDto()));

        }
    }

    @DisplayName("Check deleteCandidateById Method")
    @Nested
    class CheckDeleteCandidate {
        @DisplayName("Successfully deleted candidate")
        @Test
        void deleteCandidateById_SuccessfullyDeleted() {
            when(candidateRepository.findById(getCandidateId())).thenReturn(getOptionalCandidate());
            when(candidateRepository.save(any(Candidate.class))).thenReturn(getOptionalCandidate().get());
            candidateService.deleteCandidateById(getCandidateId());
            verify(candidateRepository, times(1)).findById(getCandidateId());
            verify(candidateRepository, times(1)).save(any(Candidate.class));
        }

        @DisplayName("Return NotFoundException when the Id Candidate's not found")
        @Test
        void deleteCandidateById_Exception() {
            when(candidateRepository.findById(getCandidateId())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> candidateService.deleteCandidateById(getCandidateId()));

        }
    }

    @DisplayName("Check deleteCandidateById Method")
    @Nested
    class CheckUpdateCandidateById {
        @DisplayName("Successfully updated candidate")
        @Test
        void updateCandidateById_SuccessfullyUpdated() {
            when(candidateRepository.findById(getCandidateId())).thenReturn(getOptionalCandidate());
            when(candidateRepository.save(any(Candidate.class))).thenReturn(getOptionalCandidate().get());
            candidateService.updateCandidateById(getCandidateId(), getCandidateDto());
            verify(candidateRepository, times(1)).findById(getCandidateId());
            verify(candidateRepository, times(1)).save(any(Candidate.class));
        }

        @DisplayName("Return NotFoundException when the Id Candidate's not found")
        @Test
        void updateCandidateById_NotFoundException() {
            when(candidateRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> candidateService.updateCandidateById(getCandidateId(), getCandidateDto()));

        }
    }

    @DisplayName("Check getCandidateById Method")
    @Nested
    class CheckGetCandidateById {
        @DisplayName("Successfully searched candidate")
        @Test
        void getCandidateById_SuccessfullySearched() {
            when(candidateRepository.findById(getCandidateId())).thenReturn(getOptionalCandidate());
            when(modelMapper.map(getOptionalCandidate().get(), CandidateDto.class)).thenReturn(getCandidateDto());
            CandidateDto candidateSearched = candidateService.getCandidateById(getCandidateId());
            verify(candidateRepository, times(1)).findById(getCandidateId());
            assertNotNull(candidateSearched);
        }

        @DisplayName("Return NotFoundException when the Id Candidate's not found")
        @Test
        void getCandidateById_Exception() {
            when(candidateRepository.findById(getCandidateId())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> candidateService.getCandidateById(getCandidateId()));

        }
    }
}
