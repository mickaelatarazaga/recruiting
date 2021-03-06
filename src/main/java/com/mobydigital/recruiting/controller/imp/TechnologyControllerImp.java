package com.mobydigital.recruiting.controller.imp;

import com.mobydigital.recruiting.controller.TechnologyController;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.service.TechnologyService;
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

@Tag(name = "Technologies")
@RestController
@RequestMapping("/technology")
public class TechnologyControllerImp implements TechnologyController {
    @Autowired
    private TechnologyService technologyService;

    @Operation(summary = "Save new Technology")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Saved Technology", content = @Content),
            @ApiResponse(responseCode = "202", description = "This Technology already exist", content = @Content)
    })
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> saveTechnology(@Valid @RequestBody TechnologyDto request) {
        technologyService.createTechnology(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Technology by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Technology", content = @Content),
            @ApiResponse(responseCode = "202", description = "Technology not found", content = @Content)

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCandidate(@PathVariable Long id) {
        technologyService.deleteTechnologyById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update Technology by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully updated Technology", content = @Content),
            @ApiResponse(responseCode = "202", description = "Technology not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateTechnology(@PathVariable Long id, @RequestBody TechnologyDto request) {
        technologyService.updateTechnology(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get Technology by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Technology", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TechnologyDto.class))),
            @ApiResponse(responseCode = "202", description = "Technology not found", content = @Content)

    })
    @GetMapping("/{id}")
    public ResponseEntity<TechnologyDto> getTechnologyById(@PathVariable Long id) {
        return new ResponseEntity<>(technologyService.getTechnologyById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get All Technology")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json")),

    })
    @GetMapping("/all")
    public ResponseEntity<List<TechnologyDto>> getAllTechnologies() {
        return new ResponseEntity<>(technologyService.getAllTechnologies(), HttpStatus.OK);
    }

}
