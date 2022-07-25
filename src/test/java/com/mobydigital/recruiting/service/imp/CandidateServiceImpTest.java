package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
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
import static com.mobydigital.recruiting.util.TestUtil.getOptionalCandidate;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    class CheckValidStringUrl {
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

}
