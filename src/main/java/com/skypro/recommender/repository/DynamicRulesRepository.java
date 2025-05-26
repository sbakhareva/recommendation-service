package com.skypro.recommender.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.model.Rule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

    public void createRule(Rule rule, UUID recommendationId) throws JsonProcessingException {
        String argumentsJson = new ObjectMapper().writeValueAsString(rule.getArguments());
        jdbcTemplate.update(
                "INSERT INTO rules " +
                        "(id, query, arguments, negate, recommendation_id) " +
                        "VALUES (?, ?, ?, ?, ?)",
                UUID.randomUUID(),
                rule.getQuery(),
                argumentsJson,
                rule.isNegate(),
                recommendationId
        );
    }

    public List<Rule> getRules(UUID recommendationId) {
        return jdbcTemplate.query(
                "SELECT * FROM rules " +
                        "WHERE recommendation_id = ?",
                new BeanPropertyRowMapper<>(Rule.class),
                recommendationId
        );
    }

    public void deleteRule(UUID ruleId) {
        jdbcTemplate.update(
                "DELETE FROM rules " +
                        "WHERE id = ?",
                ruleId
        );
    }
}
