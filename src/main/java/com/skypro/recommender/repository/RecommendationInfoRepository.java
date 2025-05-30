package com.skypro.recommender.repository;

import com.skypro.recommender.model.RecommendationInfo;
import com.skypro.recommender.model.Rule;
import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.utils.RuleRowMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий, который работает с базой данных recommendation-info
 */
@Repository
public class RecommendationInfoRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationInfoRepository(@Qualifier("recommendationsInfoJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getRecommendationName(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT name FROM recommendations WHERE id = ?",
                String.class,
                id);
    }

    public String getRecommendationDescription(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT description FROM recommendations WHERE id = ?",
                String.class,
                id);
    }

    public RecommendationDTO getRecommendation(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description FROM recommendations WHERE id = ? ",
                new BeanPropertyRowMapper<>(RecommendationDTO.class),
                id
        );
    }

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
}
