package com.mobydigital.recruiting.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyResponse {

    @Schema(name = "id", example = "6", type = "Long", required = true)
    private Long id;

    @Schema(name = "name", example = "Angular", type = "String", required = true)
    private String name;

    @Schema(name = "version", example = "12", type = "String", required = true)
    private String version;
}
