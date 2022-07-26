package com.mobydigital.recruiting.service.imp;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
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
import java.util.Optional;

import static com.mobydigital.recruiting.util.TestUtil.getListTechnology;
import static com.mobydigital.recruiting.util.TestUtil.getOptionalTechnology;
import static com.mobydigital.recruiting.util.TestUtil.getTechnologyDto;
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
            when(technologyRepository.findByNameAndVersion(getTechnologyDto().getName(), getTechnologyDto().getVersion())).thenReturn(Optional.ofNullable(null));
            when(technologyRepository.save(any(Technology.class))).thenReturn(getOptionalTechnology().get());
            when(modelMapper.map(getTechnologyDto(), Technology.class)).thenReturn(getOptionalTechnology().get());
            technologyService.createTechnology(getTechnologyDto());
            verify(technologyRepository, times(1)).findByNameAndVersion(getTechnologyDto().getName(), getTechnologyDto().getVersion());
            verify(technologyRepository, times(1)).save(any(Technology.class));
        }


        @DisplayName("Return DataAlreadyExistException when technology already exist")
        @Test
        void createTechnology_Exception() {
            when(technologyRepository.findByNameAndVersion(getTechnologyDto().getName(), getTechnologyDto().getVersion())).thenReturn(getOptionalTechnology());
            assertThrows(DataAlreadyExistException.class, () -> technologyService.createTechnology(getTechnologyDto()));

        }


        @DisplayName("Check deleteTechnologyById Method")
        @Nested
        class CheckDeleteTechnologyById {
            @DisplayName("Successfully deleted technology")
            @Test
            void deleteTechnologyById_SuccessfullyDeleted() {
                when(technologyRepository.findById(getTechnologyId())).thenReturn(getOptionalTechnology());
                technologyService.deleteTechnologyById(getTechnologyId());
                verify(technologyRepository, times(1)).findById(getTechnologyId());
                verify(technologyRepository, times(1)).delete(any(Technology.class));
            }

            @DisplayName("Return NotFoundException when the Id Technology's not found")
            @Test
            void deleteTechnologyById_Exception() {
                when(technologyRepository.findById(getTechnologyId())).thenReturn(Optional.ofNullable(null));
                assertThrows(NotFoundException.class, () -> technologyService.deleteTechnologyById(getTechnologyId()));

            }
        }

        @DisplayName("Check updateTechnology Method")
        @Nested
        class CheckUpdateTechnology {
            @DisplayName("Successfully updated technology")
            @Test
            void updateTechnology_SuccessfullyUpdated() {
                when(technologyRepository.findById(getTechnologyId())).thenReturn(getOptionalTechnology());
                when(technologyRepository.save(any(Technology.class))).thenReturn(getOptionalTechnology().get());
                technologyService.updateTechnology(getTechnologyId(), getTechnologyDto());
                verify(technologyRepository, times(1)).findById(getTechnologyId());
                verify(technologyRepository, times(1)).save(any(Technology.class));
            }

            @DisplayName("Return NotFoundException when the Id Technology's not found")
            @Test
            void updateTechnology_NotFoundException() {
                when(technologyRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
                assertThrows(NotFoundException.class, () -> technologyService.updateTechnology(getTechnologyId(), getTechnologyDto()));

            }
        }

    }


    @DisplayName("Check getTechnologyById Method")
    @Nested
    class CheckGetCandidateById {
        @DisplayName("Successfully searched experience")
        @Test
        void getTechnologyById_SuccessfullySearched() {
            when(technologyRepository.findById(getTechnologyId())).thenReturn(getOptionalTechnology());
            when(modelMapper.map(getOptionalTechnology().get(), TechnologyDto.class)).thenReturn(getTechnologyDto());
            TechnologyDto technologySearched = technologyService.getTechnologyById(getTechnologyId());
            verify(technologyRepository, times(1)).findById(getTechnologyId());
            assertNotNull(technologySearched);
        }

        @DisplayName("Return NotFoundException when the Id Technology's not found")
        @Test
        void getTechnologyById_Exception() {
            when(technologyRepository.findById(getTechnologyId())).thenReturn(Optional.ofNullable(null));
            assertThrows(NotFoundException.class, () -> technologyService.getTechnologyById(getTechnologyId()));
        }
    }


}