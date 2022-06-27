package com.mobydigital.recruiting.model.dto;

import com.mobydigital.recruiting.enums.TypeOfDni;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateRequest {

    @Schema(name = "firstName", example = "Mariana", type = "String")
    private String firstName;

    @Schema(name = "lastName", example = "Ramos", type = "String")
    private String lastName;

    @Schema(name = "typeOfDni", example = "0", type = "String")
    private TypeOfDni typeOfDni;

    @Schema(name = "dniNumber", example = "39879638", type = "String")
    private String dniNumber;

    @Schema(name = "birthday", example = "1998-11-02", type = "String")
    private Date birthday;
}
