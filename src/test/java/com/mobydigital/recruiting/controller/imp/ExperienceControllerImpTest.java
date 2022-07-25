package com.mobydigital.recruiting.controller.imp;

import com.google.gson.Gson;
import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.service.imp.ExperienceServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.mobydigital.recruiting.util.TestUtil.getCandidateId;
import static com.mobydigital.recruiting.util.TestUtil.getExperienceDto;
import static com.mobydigital.recruiting.util.TestUtil.getExperienceId;
import static com.mobydigital.recruiting.util.TestUtil.getListExperienceDto;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExperienceControllerImpTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ExperienceServiceImp experienceService;

    @Test
    @DisplayName("Successfully saved experience")
    void saveExperience_ExperienceCreated() throws Exception {
        String experienceDto = new Gson().toJson(getExperienceDto());
        mockMvc.perform(post("/experience/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experienceDto))
                .andExpect(status().isCreated());
        verify(experienceService, atLeastOnce()).createExperience(any(ExperienceDto.class));
    }

    @Test
    @DisplayName("Return DataAlreadyExistException when the experience to save already exist")
    void saveExperience_ExperienceAlreadyExist() throws Exception {
        doThrow(DataAlreadyExistException.class).when(experienceService).createExperience(getExperienceDto());
        String experienceDto = new Gson().toJson(getExperienceDto());
        mockMvc.perform(post("/experience/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experienceDto))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(DataAlreadyExistException.class, () -> experienceService.createExperience(getExperienceDto())));
        verify(experienceService, atLeastOnce()).createExperience(any(ExperienceDto.class));
    }

    @Test
    @DisplayName("Successfully updated experience")
    void updateExperience_SuccessfullyUpdated() throws Exception {
        String experienceDto = new Gson().toJson(getExperienceDto());
        mockMvc.perform(put("/experience/{id}", getExperienceId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experienceDto))
                .andExpect(status().isOk());
        verify(experienceService, atLeastOnce()).updateExperience(getExperienceId(), getExperienceDto());
    }

    @Test
    @DisplayName("Return NotFoundException when the experience's Id to update not found")
    void updateExperience_IdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(experienceService).updateExperience(getExperienceId(), getExperienceDto());
        String experienceDto = new Gson().toJson(getExperienceDto());
        mockMvc.perform(put("/experience/{id}", getExperienceId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(experienceDto))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> experienceService.updateExperience(getExperienceId(), getExperienceDto())));
        verify(experienceService, atLeastOnce()).updateExperience(getExperienceId(), getExperienceDto());
    }


    @Test
    @DisplayName("Successfully deleted experience")
    void deleteExperience_successfullyDeleted() throws Exception {
        mockMvc.perform(delete("/experience/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(experienceService, atLeastOnce()).deleteExperienceById(getExperienceId());
    }

    @Test
    @DisplayName("Return NotFoundException when the experience to delete not found")
    void deleteExperience_idNotFound() throws Exception {
        doThrow(NotFoundException.class).when(experienceService).deleteExperienceById(getExperienceId());
        mockMvc.perform(delete("/experience/{id}", getExperienceId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> experienceService.deleteExperienceById(getExperienceId())));
        verify(experienceService, atLeastOnce()).deleteExperienceById(getExperienceId());
    }

    @Test
    @DisplayName("Successful experience search")
    void getExperience_SuccessfullySearched() throws Exception {
        when(experienceService.getExperienceById(getExperienceId())).thenReturn(getExperienceDto());
        mockMvc.perform(get("/experience/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getExperienceDto());
        verify(experienceService, atLeastOnce()).getExperienceById(getExperienceId());
    }

    @Test
    @DisplayName("Return NotFoundException when the experience's Id to search not found")
    void getExperience_IdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(experienceService).getExperienceById(getExperienceId());
        mockMvc.perform(get("/experience/{id}", getExperienceId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> experienceService.getExperienceById(getCandidateId())));
        verify(experienceService, atLeastOnce()).getExperienceById(getExperienceId());
    }

    @Test
    @DisplayName("Successful experience list search ")
    void getAllExperiences_successfullySearched() throws Exception {
        when(experienceService.getAllExperiences()).thenReturn(getListExperienceDto());
        mockMvc.perform(get("/experience/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getListExperienceDto());
        verify(experienceService, atLeastOnce()).getAllExperiences();
    }

    @Test
    @DisplayName("Successful experience by candidate list search ")
    void getAllExperiencesByCandidateId_successfullySearched() throws Exception {
        when(experienceService.getAllExperiencesByCandidate(getCandidateId())).thenReturn(getListExperienceDto());
        mockMvc.perform(get("/experience/{id}/all", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getListExperienceDto());
        verify(experienceService, atLeastOnce()).getAllExperiencesByCandidate(getCandidateId());
    }

}