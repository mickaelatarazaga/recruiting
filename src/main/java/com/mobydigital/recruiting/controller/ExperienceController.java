package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.service.ExperienceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<String> saveExperience(@Valid @RequestBody ExperienceDto request) throws DataAlreadyExistException, NotFoundException {
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

    @Operation(summary = "Update Experience by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated Experience", content = @Content),
            @ApiResponse(responseCode = "404", description = "Experience not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candiate ID not found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Technology ID not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTechnology(@PathVariable Long id, @RequestBody ExperienceDto request) throws NotFoundException {
        return new ResponseEntity<>(experienceService.updateExperience(id, request), HttpStatus.OK);
    }


    @Operation(summary = "Get Experience by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Experience", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDto.class))),
            @ApiResponse(responseCode = "404", description = "Experience not found", content = @Content)

    })
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(experienceService.getExperienceById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get All Experiences")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),

    })
    @GetMapping("/all")
    public ResponseEntity<List<ExperienceDto>> getAllExperiences() {
        return new ResponseEntity<>(experienceService.getAllExperiences(), HttpStatus.OK);
    }

    @Operation(summary = "Get All Experiences by Candidate Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Candidate Id not found", content = @Content)
    })
    @GetMapping("/{id}/all")
    public ResponseEntity<List<ExperienceDto>> getAllExperiencesByCandidate(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(experienceService.getAllExperiencesByCandidate(id), HttpStatus.OK);
    }
}
