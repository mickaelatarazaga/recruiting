package com.mobydigital.recruiting.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Query {
    public static final String FIND_BY_CANDIDATE_ID_AND_TECHNOLOGY_ID = "SELECT * FROM EXPERIENCES e WHERE e.candidate_id = ?1 AND e.technology_id = ?2";
    public static final String FIND_ALL_BY_CANDIDATE_ID = "SELECT * FROM EXPERIENCES e WHERE e.candidate_id = ?1";
}
