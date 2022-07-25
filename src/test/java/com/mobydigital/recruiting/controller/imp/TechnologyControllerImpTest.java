package com.mobydigital.recruiting.controller.imp;

import com.google.gson.Gson;
import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.service.imp.TechnologyServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.mobydigital.recruiting.util.TestUtil.getCandidateId;
import static com.mobydigital.recruiting.util.TestUtil.getListTechnologyDto;
import static com.mobydigital.recruiting.util.TestUtil.getTechnologyDto;
import static com.mobydigital.recruiting.util.TestUtil.getTechnologyId;
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
class TechnologyControllerImpTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TechnologyServiceImp technologyService;

    @Test
    @DisplayName("Successfully saved technology")
    void saveTechnology_SuccessfullyCreated() throws Exception {
        String technologyDto = new Gson().toJson(getTechnologyDto());
        mockMvc.perform(post("/technology/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyDto))
                .andExpect(status().isCreated());
        verify(technologyService, atLeastOnce()).createTechnology(any(TechnologyDto.class));
    }

    @Test
    @DisplayName("Return DataAlreadyExistException when the technology to save already exist")
    void saveTechnology_ExperienceAlreadyExist() throws Exception {
        doThrow(DataAlreadyExistException.class).when(technologyService).createTechnology(getTechnologyDto());
        String technologyDto = new Gson().toJson(getTechnologyDto());
        mockMvc.perform(post("/technology/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyDto))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(DataAlreadyExistException.class, () -> technologyService.createTechnology(getTechnologyDto())));
        verify(technologyService, atLeastOnce()).createTechnology(any(TechnologyDto.class));
    }

    @Test
    @DisplayName("Successfully updated technology")
    void updateTechnology_SuccessfullyUpdated() throws Exception {
        String technologyDto = new Gson().toJson(getTechnologyDto());
        mockMvc.perform(put("/technology/{id}", getTechnologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyDto))
                .andExpect(status().isOk());
        verify(technologyService, atLeastOnce()).updateTechnology(getTechnologyId(), getTechnologyDto());
    }

    @Test
    @DisplayName("Return NotFoundException when the technology's Id to update not found")
    void updateTechnology_IdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(technologyService).updateTechnology(getTechnologyId(), getTechnologyDto());
        String technologyDto = new Gson().toJson(getTechnologyDto());
        mockMvc.perform(put("/technology/{id}", getTechnologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(technologyDto))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> technologyService.updateTechnology(getTechnologyId(), getTechnologyDto())));
        verify(technologyService, atLeastOnce()).updateTechnology(getTechnologyId(), getTechnologyDto());
    }


    @Test
    @DisplayName("Successfully deleted technology")
    void deleteTechnology_successfullyDeleted() throws Exception {
        mockMvc.perform(delete("/technology/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(technologyService, atLeastOnce()).deleteTechnologyById(getTechnologyId());
    }

    @Test
    @DisplayName("Return NotFoundException when the technology to delete not found")
    void deleteTechnology_idNotFound() throws Exception {
        doThrow(NotFoundException.class).when(technologyService).deleteTechnologyById(getTechnologyId());
        mockMvc.perform(delete("/technology/{id}", getTechnologyId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> technologyService.deleteTechnologyById(getTechnologyId())));
        verify(technologyService, atLeastOnce()).deleteTechnologyById(getTechnologyId());
    }

    @Test
    @DisplayName("Successful technology search")
    void getTechnology_SuccessfullySearched() throws Exception {
        when(technologyService.getTechnologyById(getTechnologyId())).thenReturn(getTechnologyDto());
        mockMvc.perform(get("/technology/{id}", getCandidateId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getTechnologyDto());
        verify(technologyService, atLeastOnce()).getTechnologyById(getTechnologyId());
    }

    @Test
    @DisplayName("Return NotFoundException when the technology's Id to search not found")
    void getTechnology_IdNotFound() throws Exception {
        doThrow(NotFoundException.class).when(technologyService).getTechnologyById(getTechnologyId());
        mockMvc.perform(get("/technology/{id}", getTechnologyId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertThrows(NotFoundException.class, () -> technologyService.getTechnologyById(getTechnologyId())));
        verify(technologyService, atLeastOnce()).getTechnologyById(getTechnologyId());
    }

    @Test
    @DisplayName("Successful technology list search ")
    void getAllTechnologies_successfullySearched() throws Exception {
        when(technologyService.getAllTechnologies()).thenReturn(getListTechnologyDto());
        mockMvc.perform(get("/technology/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .equals(getListTechnologyDto());
        verify(technologyService, atLeastOnce()).getAllTechnologies();
    }


}