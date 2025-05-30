package com.skypro.recommender.repository;

import com.skypro.recommender.model.dto.RecommendationDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
                "SELECT * FROM recommendations WHERE id = ? ",
                new BeanPropertyRowMapper<>(RecommendationDTO.class),
                id
        );
    }
}
