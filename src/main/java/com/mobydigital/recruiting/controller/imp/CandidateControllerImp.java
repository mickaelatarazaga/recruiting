package com.mobydigital.recruiting.controller.imp;

import com.mobydigital.recruiting.controller.CandidateController;
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

@Tag(name = "Candidates")
@RestController
@RequestMapping("/candidate")
public class CandidateControllerImp implements CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Operation(summary = "Save new Candidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Candidate", content = @Content),
            @ApiResponse(responseCode = "409", description = "The Candidate DNI number already exist", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> saveCandidate(@Valid @RequestBody CandidateDto request) {
        candidateService.createCandidate(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Candidate", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidateById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated Candidate", content = @Content),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDto request) {
        candidateService.updateCandidateByDni(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get Candidate by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Candidate", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CandidateDto.class))),
            @ApiResponse(responseCode = "404", description = "Candidate not found", content = @Content)

    })
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidate(@PathVariable Long id) {
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
