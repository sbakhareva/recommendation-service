package com.skypro.recommender.repository;


import com.skypro.recommender.model.Rule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DynamicRulesRepository {

    private final JdbcTemplate jdbcTemplate;

    public DynamicRulesRepository(@Qualifier("recommendationsInfoJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRule(Rule rule, UUID recommendation_id) {
        String query = "INSERT INTO rules (id, query, arguments, negate, recommendation_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                query,
                UUID.randomUUID(),
                rule.getQuery(),
                rule.getArguments(),
                rule.isNegate(),
                recommendation_id
        );
    }

    public String getRecommendationWithRules(UUID recommendation_id) {
        return jdbcTemplate.queryForObject(
                "SELECT " +
                        "r.name, " +
                        "r.id, " +
                        "r.description, " +
                        "ru.id AS rule_id, " +
                        "ru.query, " +
                        "ru.arguments, " +
                        "ru.negate " +
                        "FROM " +
                        "recommendations r " +
                        "JOIN " +
                        "rules ru ON r.id = ru.recommendation_id " +
                        "WHERE r.id = ? ",
                String.class,
                recommendation_id
        );
    }
}
