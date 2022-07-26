package com.mobydigital.recruiting.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Query {
    public static final String FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID = "SELECT * FROM experiences e WHERE e.candidate_id = ?1 AND e.technology_id = ?2";
    public static final String FIND_ALL_BY_CANDIDATE_ID = "SELECT * FROM experiences e WHERE e.candidate_id = ?1";
    public static final String FIND_CANDIDATE_BY_ID = "SELECT * FROM candidates c WHERE c.dni_number = ?1";
    public static final String FIND_TECHNOLOGY_BY_NAME_AND_VERSION = "SELECT * FROM technologies t WHERE t.name = ?1 AND t.version = ?2";
    public static final String FIND_CANDIDATE_BY_TECHNOLOGY_NAME = "SELECT c.first_name, c.last_name, c.type_dni ,c.dni_number, t.name, t.version, e.years_experience \n" +
            "FROM candidates c INNER JOIN experiences e  ON c.id=e.candidate_id \n" +
            "INNER JOIN technologies t ON e.technology_id=t.id WHERE t.name LIKE ?1";

}
