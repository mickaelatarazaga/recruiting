package com.mobydigital.recruiting.model.projection;

import org.springframework.beans.factory.annotation.Value;

public interface CandidateByTechnologyProjection {
    @Value("#{target.first_name}")
    String getName();

    @Value("#{target.first_name}")
    String setName(String firstName);


    @Value("#{target.last_name}")
    String getLastname();

    @Value("#{target.last_name}")
    void setLastname(String lastName);


    @Value("#{target.dni_number}")
    String getDocument();

    @Value("#{target.dni_number}")
    void setDocument(String dniNumber);

    @Value("#{target.name}")
    String getDescription();

    @Value("#{target.name}")
    void setDescription(String technologyName);

    @Value("#{target.version}")
    String getVersion();

    @Value("#{target.version}")
    void setVersion(String version);

    @Value("#{target.years_experience}")
    Integer getExperienceYears();

    @Value("#{target.years_experience}")
    void setExperienceYears(Integer yearsExperience);
}
