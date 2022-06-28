package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Experiences")
@RestController
@RequestMapping("/experience")
public class ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @Operation(summary = "Save new Experience")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Experience", content = @Content),
            @ApiResponse(responseCode = "409", description = "This Experience already exist", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<String> saveExperience(@Valid @RequestBody ExperienceDto request) throws DataAlreadyExistException {
        return new ResponseEntity<>(experienceService.createExperience(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Experience by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Experience", content = @Content),
            @ApiResponse(responseCode = "404", description = "Experience not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExperience(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(experienceService.deleteExperienceById(id), HttpStatus.OK);
    }
}
