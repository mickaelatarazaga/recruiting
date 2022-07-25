package com.mobydigital.recruiting.controller.imp;

import com.google.gson.Gson;
import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.service.imp.CandidateServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.mobydigital.recruiting.util.TestUtil.getCandidateDto;
import static com.mobydigital.recruiting.util.TestUtil.getCandidateId;
import static com.mobydigital.recruiting.util.TestUtil.getListCandidateDto;
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
@AutoConfigureMockMvc
class CandidateControllerImpTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CandidateServiceImp candidateService;

    @Test
    @DisplayName("Successfully saved candidate")
    void saveCandidate_candidateCreated() throws Exception {
        String candidateDto = new Gson().toJson(getCandidateDto());
        mockMvc.perform(post("/candidate/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateDto))
                .andExpect(status().isCreated());
        verify(candidateService, atLeastOnce()).createCandidate(any(CandidateDto.class));
    }

    @Test
    @DisplayName("Return DataAlreadyExistException when the candidate's DNI to save already exist")
    void saveCandidate_candidateAlreadyExist() throws Exception {
        doThrow(DataAlreadyExistException.class).when(candidateService).createCandidate(getCandidateDto());
        String candidateDto = new Gson().toJson(getCandidateDto());
        mockMvc.perform(post("/candidate/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateDto))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(DataAlreadyExistException.class, () -> candidateService.createCandidate(getCandidateDto())));
        verify(candidateService, atLeastOnce()).createCandidate(any(CandidateDto.class));
    }

    @Test
    @DisplayName("Successfully updated candidate")
    void updateCandidate_candidateUpdated() throws Exception {
        String candidateDto = new Gson().toJson(getCandidateDto());
        mockMvc.perform(put("/candidate/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateDto))
                .andExpect(status().isOk());
        verify(candidateService, atLeastOnce()).updateCandidateById(getCandidateId(), getCandidateDto());
    }

    @Test
    @DisplayName("Return NotFoundException when the candidate's Id to update not found")
    void updateCandidate_candidateIdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(candidateService).updateCandidateById(getCandidateId(), getCandidateDto());
        String candidateDto = new Gson().toJson(getCandidateDto());
        mockMvc.perform(put("/candidate/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateDto))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> candidateService.updateCandidateById(getCandidateId(), getCandidateDto())));
        verify(candidateService, atLeastOnce()).updateCandidateById(getCandidateId(), getCandidateDto());
    }


    @Test
    @DisplayName("Successfully deleted candidate")
    void deleteCandidate_successfullyDeleted() throws Exception {
        mockMvc.perform(delete("/candidate/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(candidateService, atLeastOnce()).deleteCandidateById(getCandidateId());
    }

    @Test
    @DisplayName("Return NotFoundException when the candidate's Id to delete not found")
    void deleteCandidate_idNotFound() throws Exception {
        doThrow(NotFoundException.class).when(candidateService).deleteCandidateById(getCandidateId());
        mockMvc.perform(delete("/candidate/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> candidateService.deleteCandidateById(getCandidateId())));
        verify(candidateService, atLeastOnce()).deleteCandidateById(getCandidateId());
    }

    @Test
    @DisplayName("Successful candidate search")
    void getCandidate_SuccessfullySearched() throws Exception {
        when(candidateService.getCandidateById(getCandidateId())).thenReturn(getCandidateDto());
        mockMvc.perform(get("/candidate/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getCandidateDto());
        verify(candidateService, atLeastOnce()).getCandidateById(getCandidateId());
    }

    @Test
    @DisplayName("Return NotFoundException when the candidate's Id to search not found")
    void getCandidate_IdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(candidateService).getCandidateById(getCandidateId());
        mockMvc.perform(get("/candidate/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> candidateService.getCandidateById(getCandidateId())));
        verify(candidateService, atLeastOnce()).getCandidateById(getCandidateId());
    }

    @Test
    @DisplayName("Successful candidate list search ")
    void getAllCandidates_successfullySearched() throws Exception {
        when(candidateService.getAllCandidates()).thenReturn(getListCandidateDto());
        mockMvc.perform(get("/candidate/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getListCandidateDto());
        verify(candidateService, atLeastOnce()).getAllCandidates();
    }
}