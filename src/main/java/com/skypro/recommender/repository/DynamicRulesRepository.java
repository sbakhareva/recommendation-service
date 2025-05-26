package com.skypro.recommender.repository;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class DynamicRulesRepository {

    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesRepository(@Qualifier("recommendationsInfoJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String createRule(String query, List<String> arguments, boolean negate) {

        return null;
    }
}
