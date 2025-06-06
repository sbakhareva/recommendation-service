package com.skypro.recommender.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.recommender.model.RecommendationInfo;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.Recommendation;
import com.skypro.recommender.utils.RuleRowMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Репозиторий, который работает с базой данных recommendation-info
 */
@Repository
public class RecommendationInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationInfoRepository(@Qualifier("recommendationsInfoJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRecommendationWithRules(Recommendation recommendation) throws JsonProcessingException {
        jdbcTemplate.update(
                "INSERT INTO recommendations " +
                        "(id, name, description) " +
                        "VALUES (?, ?, ?) ",
                recommendation.getId(),
                recommendation.getName(),
                recommendation.getDescription()
                );
        List<Rule> rules = recommendation.getRules();
        for (Rule rule : rules) {
            String argumentsJson = new ObjectMapper().writeValueAsString(rule.getArguments());
            jdbcTemplate.update(
                    "INSERT INTO rules " +
                            "(id, query, arguments, negate, recommendation_id, counter) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    UUID.randomUUID(),
                    rule.getQuery(),
                    argumentsJson,
                    rule.getNegate(),
                    recommendation.getId(),
                    0
            );
        }
    }

    @Cacheable(value = "getRecName", key = "#recommendationId")
    public String getRecommendationName(UUID recommendationId) {
        return jdbcTemplate.queryForObject(
                "SELECT name FROM recommendations WHERE id = ?",
                String.class,
                recommendationId);
    }

    @Cacheable(value = "getRecDescription", key = "#recommendationId")
    public String getRecommendationDescription(UUID recommendationId) {
        return jdbcTemplate.queryForObject(
                "SELECT description FROM recommendations WHERE id = ?",
                String.class,
                recommendationId);
    }

    @Cacheable(value = "recommendation", key = "#recommendationId")
    public Recommendation getRecommendation(UUID recommendationId) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description FROM recommendations WHERE id = ? ",
                new BeanPropertyRowMapper<>(Recommendation.class),
                recommendationId
        );
    }

    @Cacheable(value = "recWithRules", key = "#recommendationId")
    public RecommendationInfo getRecommendationWithRules(UUID recommendationId) {
        RecommendationInfo recommendation = jdbcTemplate.queryForObject(
                "SELECT id, name, description " +
                        "FROM recommendations " +
                        "WHERE id = ? ",
                (rs, rowNum) -> {
                    RecommendationInfo rec = new RecommendationInfo();
                    rec.setName(rs.getString("name"));
                    rec.setId(UUID.fromString(rs.getString("id")));
                    rec.setDescription(rs.getString("description"));
                    return rec;
                },
                recommendationId
        );
        List<Rule> rules = jdbcTemplate.query(
                "SELECT * FROM rules WHERE recommendation_id = ?",
                new RuleRowMapper(),
                recommendationId

        );
        recommendation.setRules(rules);
        return recommendation;
    }

    @Cacheable("allRecs")
    public List<Recommendation> getAllRecommendations() {
        Map<UUID, Recommendation> recommendationMap = new HashMap<>();

        String recommendationsSql = "SELECT id, name, description FROM recommendations";
        jdbcTemplate.query(recommendationsSql, (rs) -> {
            UUID recId = rs.getObject("id", UUID.class);
            Recommendation rec = new Recommendation();
            rec.setId(recId);
            rec.setName(rs.getString("name"));
            rec.setDescription(rs.getString("description"));
            rec.setRules(new ArrayList<>());
            recommendationMap.put(recId, rec);
        });

        String rulesSql = "SELECT id, query, arguments, negate, recommendation_id FROM rules";
        List<Rule> rules = jdbcTemplate.query(rulesSql, new RuleRowMapper());

        for (Rule rule : rules) {
            UUID recId = rule.getRecommendation_id();
            Recommendation rec = recommendationMap.get(recId);
            if (rec != null) {
                rec.getRules().add(rule);
            }
        }

        return new ArrayList<>(recommendationMap.values());
    }
}
