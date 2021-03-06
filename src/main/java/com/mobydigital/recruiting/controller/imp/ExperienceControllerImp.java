package com.mobydigital.recruiting.controller.imp;

import com.mobydigital.recruiting.controller.ExperienceController;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.projection.CandidateByTechnologyProjection;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Experiences")
@RestController
@RequestMapping("/experience")
public class ExperienceControllerImp implements ExperienceController {
    @Autowired
    private ExperienceService experienceService;

    @Operation(summary = "Save new Experience")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Experience", content = @Content),
            @ApiResponse(responseCode = "202", description = "This Experience already exist", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> saveExperience(@Valid @RequestBody ExperienceDto request) {
        experienceService.createExperience(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Experience by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Experience", content = @Content),
            @ApiResponse(responseCode = "202", description = "Experience not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteExperience(@PathVariable Long id) {
        experienceService.deleteExperienceById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update Experience by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated Experience", content = @Content),
            @ApiResponse(responseCode = "202", description = "Experience not found", content = @Content),
            @ApiResponse(responseCode = "202", description = "Candidate ID not found", content = @Content),
            @ApiResponse(responseCode = "202", description = "Technology ID not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateExperience(@PathVariable Long id, @RequestBody ExperienceDto request) {
        experienceService.updateExperience(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "Get Experience by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Experience", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDto.class))),
            @ApiResponse(responseCode = "202", description = "Experience not found", content = @Content)

    })
    @GetMapping("/{id}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long id) {
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
            @ApiResponse(responseCode = "202", description = "Candidate Id not found", content = @Content)
    })
    @GetMapping("/{id}/all")
    public ResponseEntity<List<ExperienceDto>> getAllExperiencesByCandidate(@PathVariable Long id) {
        return new ResponseEntity<>(experienceService.getAllExperiencesByCandidate(id), HttpStatus.OK);
    }

    @Operation(summary = "Get All Candidates by TechnologyName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json"))
    })
    @GetMapping(value = "/all/{technologyName}")
    public ResponseEntity<List<CandidateByTechnologyProjection>> findCandidatesByTechnologyName(String technologyName) {
        return new ResponseEntity<>(experienceService.findCandidatesByTechnologyName(technologyName), HttpStatus.OK);
    }

}
