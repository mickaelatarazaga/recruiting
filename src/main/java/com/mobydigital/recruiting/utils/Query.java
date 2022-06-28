package com.mobydigital.recruiting.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Query {

    public static final String FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID = "SELECT * FROM EXPERIENCES e WHERE e.candidate_id = ?1 AND e.technology_id = ?2";
}
