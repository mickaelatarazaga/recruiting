package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.repository.TechnologyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.mobydigital.recruiting.util.TestUtil.getListTechnology;
import static com.mobydigital.recruiting.util.TestUtil.getOptionalTechnology;
import static com.mobydigital.recruiting.util.TestUtil.getTechnologyDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TechnologyServiceImpTest {
    @InjectMocks
    private TechnologyServiceImp technologyService;
    @Mock
    private TechnologyRepository technologyRepository;

    @Mock
    private ModelMapper modelMapper;


    @DisplayName("Successfully searched all technologies")
    @Test
    void getAllTechnologies_SuccessfullySearched() {
        when(technologyRepository.findAll()).thenReturn(getListTechnology());
        when(modelMapper.map(getOptionalTechnology().get(), TechnologyDto.class)).thenReturn(getTechnologyDto());
        List<TechnologyDto> technologiesSearched = technologyService.getAllTechnologies();
        verify(technologyRepository, times(1)).findAll();
        assertEquals(getTechnologyDto(), technologiesSearched.get(0));
    }

    @DisplayName("Check createTechnology Method")
    @Nested
    class CheckCreateTechnology {
        @DisplayName("Successfully saved technology")
        @Test
        void createTechnology_SuccessfullySaved() {
            when(technologyRepository.findAll()).thenReturn(null);
            when(technologyRepository.save(any(Technology.class))).thenReturn(getOptionalTechnology().get());
            when(modelMapper.map(getTechnologyDto(), Technology.class)).thenReturn(getOptionalTechnology().get());
            technologyService.createTechnology(getTechnologyDto());
            verify(technologyRepository, times(1)).findAll();
            verify(technologyRepository, times(1)).save(any(Technology.class));
        }


        @DisplayName("Return DataAlreadyExistException when the experience by Candidate already exist")
        @Test
        void createTechnology_Exception() {
            when(technologyRepository.findAll()).thenReturn(getListTechnology());
            assertThrows(DataAlreadyExistException.class, () -> technologyService.createTechnology(getTechnologyDto()));

        }
    }


}