package com.mobydigital.recruiting.controller;

import com.mobydigital.recruiting.exeption.DataAlreadyExistException;
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


}
