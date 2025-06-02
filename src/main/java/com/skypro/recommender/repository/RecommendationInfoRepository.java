package com.skypro.recommender.repository;

import com.skypro.recommender.model.RecommendationInfo;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.dto.RecommendationDTO;
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

    @Cacheable(value = "getRecName", key = "#id")
    public String getRecommendationName(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT name FROM recommendations WHERE id = ?",
                String.class,
                id);
    }

    @Cacheable(value = "getRecDescription", key = "#id")
    public String getRecommendationDescription(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT description FROM recommendations WHERE id = ?",
                String.class,
                id);
    }

    @Cacheable(value = "recommendation", key = "#id")
    public RecommendationDTO getRecommendation(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description FROM recommendations WHERE id = ? ",
                new BeanPropertyRowMapper<>(RecommendationDTO.class),
                id
        );
    }

    @Cacheable(value = "recWithRules", key = "#id")
    public RecommendationInfo getRecommendationWithRules(UUID id) {
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
                id
        );
        List<Rule> rules = jdbcTemplate.query(
                "SELECT * FROM rules WHERE recommendation_id = ?",
                new RuleRowMapper(),
                id

        );
        recommendation.setRules(rules);
        return recommendation;
    }

    @Cacheable("allRecs")
    public List<RecommendationDTO> getAllRecommendations() {
        Map<UUID, RecommendationDTO> recommendationMap = new HashMap<>();

        String recommendationsSql = "SELECT id, name, description FROM recommendations";
        jdbcTemplate.query(recommendationsSql, (rs) -> {
            UUID recId = rs.getObject("id", UUID.class);
            RecommendationDTO rec = new RecommendationDTO();
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
            RecommendationDTO rec = recommendationMap.get(recId);
            if (rec != null) {
                rec.getRules().add(rule);
            }
        }

        return new ArrayList<>(recommendationMap.values());
    }
}
