package com.mobydigital.recruiting.util;

import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.enums.TypeOfDni;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtil {
    public static Long getCandidateId() {
        return 1L;
    }

    public static CandidateDto getCandidateDto() {
        return CandidateDto
                .builder().firstName("Mickaela")
                .lastName("Tarazaga")
                .birthDate("1998-11-02")
                .typeOfDni(TypeOfDni.DNI)
                .dniNumber("47456345")
                .build();
    }

    public static List<CandidateDto> getListCandidateDto() {
        return new ArrayList<>(Arrays.asList(getCandidateDto()));
    }

}
