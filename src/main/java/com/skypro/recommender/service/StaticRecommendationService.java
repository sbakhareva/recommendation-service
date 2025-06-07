package com.skypro.recommender.service;

import com.skypro.recommender.model.dto.RecommendationDTO;
import com.skypro.recommender.model.dto.RecommendationDTOMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Сервис, работающий со статическими правилами, прописанными в коде
 */
@Service
public class StaticRecommendationService {

    Logger logger = LoggerFactory.getLogger(StaticRecommendationService.class);

    private final List<RecommendationRuleSet> rules;
    private final DataSource dataSource;

    public StaticRecommendationService(List<RecommendationRuleSet> rules,
                                       @Qualifier("recommendationsInfoDataSource") DataSource dataSource) {
        this.rules = rules;
        this.dataSource = dataSource;
    }

    public List<RecommendationDTO> getRecommendations(UUID userId) {
        try (Connection conn = dataSource.getConnection()) {
            logger.info("########Connection established!########");
        } catch (SQLException ex) {
            logger.error("########Error while establishing connection########", ex);
        }
        return rules.stream()
                .map(rule -> rule.getRecommendation(userId))
                .flatMap(Optional::stream)
                .toList();
    }
}
