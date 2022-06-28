package com.mobydigital.recruiting.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyDto {

    @Schema(name = "name", example = "Angular", type = "String", required = true)
    private String name;

    @Schema(name = "version", example = "12", type = "String", required = true)
    private String version;
}
