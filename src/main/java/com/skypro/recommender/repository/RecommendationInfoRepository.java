package com.skypro.recommender.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

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
}
