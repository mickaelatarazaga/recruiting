package com.mobydigital.recruiting.model.dto;

import com.mobydigital.recruiting.enums.TypeOfDni;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {

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

    @NotBlank(message = "Birthday cannot be blank")
    @Schema(name = "birthday", example = "1998-11-02", type = "String", required = true)
    private String birthday;
}
