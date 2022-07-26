package com.mobydigital.recruiting.util;

import com.mobydigital.recruiting.model.dto.CandidateDto;
import com.mobydigital.recruiting.model.dto.ExperienceDto;
import com.mobydigital.recruiting.model.dto.TechnologyDto;
import com.mobydigital.recruiting.model.entity.Candidate;
import com.mobydigital.recruiting.model.entity.Experience;
import com.mobydigital.recruiting.model.entity.Technology;
import com.mobydigital.recruiting.model.enums.TypeOfDni;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtil {
    public static Long getCandidateId() {
        return 1L;
    }

    public static Long getExperienceId() {
        return 1L;
    }

    public static Long getTechnologyId() {
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

    public static Optional<Candidate> getOptionalCandidate() {

        return Optional.ofNullable(Candidate
                .builder().firstName("Mickaela")
                .lastName("Tarazaga")
                .typeOfDni(TypeOfDni.DNI)
                .dniNumber("47456345")
                .build());
    }

    public static Optional<Technology> getOptionalTechnology() {

        return Optional.ofNullable(Technology
                .builder()
                .id(1L)
                .name("Phyton")
                .version("3.10.5")
                .build());
    }

    public static Optional<Experience> getOptionalExperience() {

        return Optional.ofNullable(Experience
                .builder()
                .id(1L)
                .yearsExperience(6)
                .technology(getOptionalTechnology().get())
                .candidate(getOptionalCandidate().get())
                .build());
    }

    public static List<CandidateDto> getListCandidateDto() {
        return new ArrayList<>(Arrays.asList(getCandidateDto()));
    }

    public static List<Candidate> getListCandidate() {
        return new ArrayList<>(Arrays.asList(getOptionalCandidate().get()));
    }

    public static List<Experience> getListExperience() {
        return new ArrayList<>(Arrays.asList(getOptionalExperience().get()));
    }

    public static List<Technology> getListTechnology() {
        return new ArrayList<>(Arrays.asList(getOptionalTechnology().get()));
    }

    public static ExperienceDto getExperienceDto() {
        return ExperienceDto.builder()
                .candidateId(1L)
                .technologyId(1L)
                .yearsExperience(5)
                .build();
    }

    public static List<ExperienceDto> getListExperienceDto() {
        return new ArrayList<>(Arrays.asList(getExperienceDto()));
    }

    public static TechnologyDto getTechnologyDto() {
        return TechnologyDto.builder()
                .name("Python")
                .version("3.10.5")
                .build();
    }

    public static List<TechnologyDto> getListTechnologyDto() {
        return new ArrayList<>(Arrays.asList(getTechnologyDto()));
    }

    public static Experience getExperience() {
        return Experience.builder()
                .id(1L)
                .yearsExperience(6)
                .technology(getOptionalTechnology().get())
                .candidate(getOptionalCandidate().get())
                .build();

    }

}
