package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.CandidateRequest;
import com.mobydigital.recruiting.service.CandidateService;
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

@Tag(name = "Candidates")
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Save new Candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Candidate", content = @Content),
            @ApiResponse(responseCode = "409", description = "The Candidate DNI number already exist", content = @Content)
    })
    @PostMapping("/create")
    public ResponseEntity<String> registerUser(@Valid @RequestBody CandidateRequest request) throws DataAlreadyExistException {
        return new ResponseEntity<>(candidateService.create(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Candidate", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(candidateService.delete(id), HttpStatus.OK);
    }
}
