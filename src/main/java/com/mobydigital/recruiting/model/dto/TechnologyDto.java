package com.mobydigital.recruiting.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDto {

    @NotBlank(message = "Name cannot be blank")
    @Schema(name = "name", example = "Angular", type = "String", required = true)
    private String name;

    @Schema(name = "version", example = "12", type = "String", required = true)
    private String version;
}
