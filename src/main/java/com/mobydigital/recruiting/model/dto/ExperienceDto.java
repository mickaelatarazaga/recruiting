package com.mobydigital.recruiting.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

    @Schema(name = "candidateId", example = "1", type = "String", required = true)
    private Long candidateId;

    @Schema(name = "technologyId", example = "2", type = "String", required = true)
    private Long technologyId;

    @Schema(name = "yearsExperience", example = "2", type = "String", required = true)
    private Integer yearsExperience;
}
