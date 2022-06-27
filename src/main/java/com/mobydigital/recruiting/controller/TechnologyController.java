package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
import com.mobydigital.recruiting.exeption.NotFoundException;
import com.mobydigital.recruiting.model.dto.TechnologyRequest;
import com.mobydigital.recruiting.service.TechnologyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/technology")
public class TechnologyController {
    @Autowired
    private TechnologyService technologyService;

    @Operation(summary = "Save new Technology")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Technology", content = @Content),
            @ApiResponse(responseCode = "409", description = "This Technology already exist", content = @Content)
    })
    @PostMapping()
    public ResponseEntity<String> saveTechnology(@Valid @RequestBody TechnologyRequest request) throws DataAlreadyExistException {
        return new ResponseEntity<>(technologyService.createTechnology(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Technology by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Technology", content = @Content),
            @ApiResponse(responseCode = "404", description = "Technology not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) throws NotFoundException {
        return new ResponseEntity<>(technologyService.deleteTechnologyById(id), HttpStatus.OK);
    }


}
