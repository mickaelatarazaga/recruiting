package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.exception.DataAlreadyExistException;
import com.mobydigital.recruiting.exception.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.service.CandidateService;
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
import java.text.ParseException;
import java.util.List;

@Tag(name = "Candidates")
@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Save new Candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Candidate", content = @Content),
            @ApiResponse(responseCode = "409", description = "The Candidate DNI number already exist", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<String> saveCandidate(@Valid @RequestBody CandidateDto request) throws DataAlreadyExistException {
        return new ResponseEntity<>(candidateService.createCandidate(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Candidate", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(candidateService.deleteCandidateById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated Candidate", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDto request) throws NotFoundException, ParseException {
        return new ResponseEntity<>(candidateService.updateCandidateByDni(id, request), HttpStatus.OK);
    }

    @Operation(summary = "Get Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Candidate", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandidateDto.class))),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)

    })
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(candidateService.getCandidateById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get All Candidates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Candidate.class))),

    })
    @GetMapping("/all")
    public ResponseEntity<List<CandidateDto>> getAllCandidates() {
        return new ResponseEntity<>(candidateService.getAllCandidates(), HttpStatus.OK);
    }

}
