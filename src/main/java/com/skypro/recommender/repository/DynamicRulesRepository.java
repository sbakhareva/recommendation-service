package com.skypro.recommender.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.RuleStatistics;
import com.skypro.recommender.model.StatisticItem;
import com.skypro.recommender.utils.RuleRowMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Репозиторий, который работает с базой данных recommendation-info, в основном с таблицей rules
 */
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
                        "(id, query, arguments, negate, recommendation_id, counter) " +
                        "VALUES (?, ?, ?, ?, ?)",
                UUID.randomUUID(),
                rule.getQuery(),
                argumentsJson,
                rule.getNegate(),
                recommendationId,
                0
        );
    }

    @Cacheable(value = "getRules", key = "#recommendationId")
    public List<Rule> getRules(UUID recommendationId) {
        return jdbcTemplate.query(
                "SELECT * FROM rules " +
                        "WHERE recommendation_id = ?",
                new RuleRowMapper(),
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

    @Cacheable("allRules")
    public List<Rule> getAllRules() {
        return jdbcTemplate.query(
                "SELECT * FROM rules",
                new BeanPropertyRowMapper<>(Rule.class)
        );
    }

    public void incrementCounter(UUID ruleId) {
        jdbcTemplate.update(
                "UPDATE rules SET counter = (counter + 1) WHERE id = ?",
                ruleId);
    }

    public RuleStatistics getRulesStatistics() {
        String request = "SELECT id AS ruleId, " +
                "counter AS count " +
                "FROM rules";
        List<StatisticItem> stats =  jdbcTemplate.query(
                request, (s, rowNum) -> {
                    return new StatisticItem(
                            UUID.fromString(s.getString("id")),
                            s.getInt("counter"));
                }
        );
        return new RuleStatistics(stats);
    }

    public void resetStatistics() {
        jdbcTemplate.update(
                "UPDATE rules SET counter = 0"
        );
    }
}
