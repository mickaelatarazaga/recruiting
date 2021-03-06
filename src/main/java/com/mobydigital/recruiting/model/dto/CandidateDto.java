package com.mobydigital.recruiting.model.dto;

import com.mobydigital.recruiting.model.enums.TypeOfDni;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateDto {

    @NotBlank(message = "First Name cannot be blank")
    @Schema(name = "firstName", example = "Mariana", type = "String", required = true)
    private String firstName;

    @NotBlank(message = "Last Name cannot be blank")
    @Schema(name = "lastName", example = "Ramos", type = "String", required = true)
    private String lastName;

    @Schema(name = "typeOfDni", example = "DNI", type = "String", required = true)
    private TypeOfDni typeOfDni;

    @NotBlank(message = "DNI number cannot be blank")
    @Schema(name = "dniNumber", example = "39879638", type = "String", required = true)
    private String dniNumber;

    @NotBlank(message = "birthDate cannot be blank")
    @Schema(name = "birthDate", example = "1998-11-02", type = "String", required = true)
    private String birthDate;
}
